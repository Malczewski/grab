package edu.sl.grabalyze.grabber.strategy.telegraf;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;

import java.util.Date;
import java.util.Map;

public class TelegrafFactory extends GrabberStrategyFactory {

    private ArticleDAO articleDAO;

    public TelegrafFactory(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public GrabberStrategy createListStrategy(Date from, Date to) {
        TelegrafListStrategy result = new TelegrafListStrategy(from, to);
        result.setArticleDAO(articleDAO);
        return result;
    }

    @Override
    public GrabberStrategy createItemStrategy(Map<Long, String> urls) {
        TelegrafArticleStrategy result = new TelegrafArticleStrategy(urls);
        result.setArticleDAO(articleDAO);
        return result;
    }
}
