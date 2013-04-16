package edu.sl.grabalyze.processing.factory;

import java.util.ArrayList;
import java.util.List;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.execution.Callback;
import edu.sl.grabalyze.execution.RunnableFactory;
import edu.sl.grabalyze.processing.PostProcessor;
import edu.sl.grabalyze.processing.ProcessorImpl;
import edu.sl.grabalyze.processing.TextProcessor;

public class ProcessingFactory implements RunnableFactory {

    private int countPerWorker;
    private int offset;
    private ArticleDAO articleDAO;
    private TokenDAO tokenDAO;
    private TextProcessor textProcessor;

    public void setCountPerWorker(int count) {
        this.countPerWorker = count;
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
    public List<Runnable> create(int count) {
        List<Article> articles = MockList.createArticles();//articleDAO.getArticles(count * countPerWorker, offset);

        List<Runnable> result = new ArrayList<Runnable>(count);
        for (int i = 0; i < count; i++) {
            ProcessorImpl proc = new ProcessorImpl();
            proc.setTextProcessor(textProcessor);
            proc.setId(i + 1);
            int start = i * countPerWorker;
            int end = Math.min((i + 1) * countPerWorker, articles.size());
            if (start >= articles.size())
                break;
            proc.setArticles(articles.subList(start, end));
            result.add(proc);
            if (end >= articles.size())
                break;
        }
        
        return result;
    }

    @Override
    public Callback<List<Runnable>> createCallback() {
        return new PostProcessor(tokenDAO);
    }

}
