package edu.sl.grabalyze.processing.post;

import java.util.*;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.entity.Token;
import edu.sl.grabalyze.processing.ProcessorImpl;
import edu.sl.grabalyze.utils.ProgressMonitor;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabasePostProcessor implements PostProcessor {
    @Autowired
    private TokenDAO tokenDAO;
    
    public DatabasePostProcessor() {}
    
    @Override
    public void onSuccess(List<Runnable> result) {
        System.out.println("Post processing started.");
        List<Token> tokens = tokenDAO.getTokens();
        System.out.println("Got " + tokens.size() + " tokens.");
        long maxToken = tokens.size() > 0 ? tokens.get(tokens.size() - 1).getId() : 0;
        
        HashMap<String, Long> tokenIds = new HashMap<>(tokens.size());
        for (Token t : tokens) {
            tokenIds.put(t.getWord(), t.getId());
        }
        
        Map<Article, Map<String, Integer>> total = new HashMap<>(1000);
        for (Runnable proc : result) {
             total.putAll(((ProcessorImpl)proc).getMappings());
        }
        
        List<Token> newTokens = new ArrayList<Token>();
        Map<Article, Map<Long, Integer>> articles = new HashMap<>(total.size());
        
        ProgressMonitor mon = new ProgressMonitor(total.size());
        System.out.println("Start processing tokens(" + total.size() + " articles):");
        for (Article article : total.keySet()) {
            Map<String, Integer> counts = total.get(article);
            Map<Long, Integer> tokenCounts = new HashMap<Long, Integer>(counts.size());
            Long tokenId;
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                tokenId = tokenIds.get(entry.getKey());
                if (tokenId == null) {
                    tokenId = ++maxToken;
                    newTokens.add(new Token(tokenId, entry.getKey()));
                    tokenIds.put(entry.getKey(), tokenId);
                }
                tokenCounts.put(tokenId, entry.getValue());
            }
            articles.put(article, tokenCounts);
            mon.increment();
        }
        System.out.println("Creating " + newTokens.size() + " new tokens.");
        tokenDAO.batchInsert(newTokens);

        System.out.println("Cleaning old counts.");
        tokenDAO.clearArticleTokens(articles.keySet());

        System.out.println("Saving counts:");
        mon = new ProgressMonitor(articles.size());
        for (Article article : articles.keySet()) {
            tokenDAO.saveArticleTokens(article.getId(), articles.get(article));
            mon.increment();
        }
        System.out.println("Post processing finished.");
    }

}
