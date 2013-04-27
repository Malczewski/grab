package edu.sl.grabalyze.grabber.strategy.gazetaua;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GazetaUaFactory extends GrabberStrategyFactory {

    private ArticleDAO articleDAO;

    public GazetaUaFactory(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public GrabberStrategy createListStrategy(List<Date> dates) {
        GazetaUaListStrategy result = new GazetaUaListStrategy(dates);
        result.setArticleDAO(articleDAO);
        return result;
    }

    @Override
    public GrabberStrategy createItemStrategy(Map<Long, String> urls) {
        GazetaUaArticleStrategy result = new GazetaUaArticleStrategy(urls);
        result.setArticleDAO(articleDAO);
        return result;
    }
}
