package edu.sl.grabalyze.processing;

import java.util.*;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Token;
import edu.sl.grabalyze.execution.Callback;

public class PostProcessor implements Callback<List<Runnable>> {

    private TokenDAO tokenDAO;
    
    public PostProcessor(TokenDAO dao) {
        this.tokenDAO = dao;
    }
    
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
        
        Map<Long, Map<String, Integer>> total = new HashMap<>(1000);
        for (Runnable proc : result) {
             total.putAll(((ProcessorImpl)proc).getMappings());
        }
        
        List<Token> newTokens = new ArrayList<Token>();
        Map<Long, Map<Long, Integer>> articles = new HashMap<>(total.size());
        
        int counter = 0, last = 0;
        int size = total.size();
        System.out.println("Start processing tokens(" + size + " articles):");
        for (Long articleId : total.keySet()) {
            Map<String, Integer> counts = total.get(articleId);
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
            articles.put(articleId, tokenCounts);
            counter++;
            if ((counter * 100 / size) / 5 != last) {
                last = (counter * 100 / size) / 5;
                System.out.println("Processed: " + (counter * 100 / size) + "%");
            }
        }
        System.out.println("Creating " + newTokens.size() + " new tokens.");
        tokenDAO.batchInsert(newTokens);

        System.out.println("Cleaning old counts.");
        tokenDAO.clearArticleTokens(articles.keySet());

        System.out.println("Saving counts:");
        counter = 0; last = 0;
        size = articles.size();
        for (Long articleId : articles.keySet()) {
            tokenDAO.saveArticleTokens(articleId, articles.get(articleId));
            counter++;
            if ((counter * 100 / size) / 5 != last) {
                last = (counter * 100 / size) / 5;
                System.out.println("Processed: " + (counter * 100 / size) + "%");
            }
        }
        System.out.println("Post processing finished.");
    }

}
