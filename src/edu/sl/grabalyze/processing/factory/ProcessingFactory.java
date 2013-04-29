package edu.sl.grabalyze.processing.factory;

import java.util.ArrayList;
import java.util.List;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.execution.Callback;
import edu.sl.grabalyze.execution.RunnableFactory;
import edu.sl.grabalyze.grabber.factory.util.Distributor;
import edu.sl.grabalyze.processing.PostProcessor;
import edu.sl.grabalyze.processing.ProcessorImpl;
import edu.sl.grabalyze.processing.TextProcessor;

public class ProcessingFactory implements RunnableFactory {

    private int count;
    private int offset;
    private ArticleDAO articleDAO;
    private TokenDAO tokenDAO;
    private TextProcessor textProcessor;

    public void setCount(int count) {
        this.count = count;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setArticleDAO(ArticleDAO dao) {
        this.articleDAO = dao;
    }
    
    public void setTokenDAO(TokenDAO dao) {
        this.tokenDAO = dao;
    }
    
    public void setTextProcessor(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public List<Runnable> create(int threads) {
        //List<Article> articles = MockList.createArticles();
        System.out.println("Requesting " + count + " articles(offset=" + offset + ") for " + threads + " workers.");
        List<Article> articles = articleDAO.getArticles(count, offset);
        System.out.println("Got " + articles.size() + " articles.");

        List<Runnable> result = new ArrayList<Runnable>(threads);
        List<List<Article>> lists = new Distributor<Article>(articles).distribute(threads);
        int id = 1;
        for (List<Article> list : lists) {
            ProcessorImpl proc = new ProcessorImpl();
            proc.setTextProcessor(textProcessor);
            proc.setId(id++);
            proc.setArticles(list);
            result.add(proc);
        }
        
        return result;
    }

    @Override
    public Callback<List<Runnable>> createCallback() {
        return new PostProcessor(tokenDAO);
    }

}
