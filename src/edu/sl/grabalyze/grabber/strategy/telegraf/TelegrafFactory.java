package edu.sl.grabalyze.grabber.strategy.telegraf;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TelegrafFactory extends GrabberStrategyFactory {

    private ArticleDAO articleDAO;

    public TelegrafFactory(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public GrabberStrategy createListStrategy(List<Date> dates) {
        TelegrafListStrategy result = new TelegrafListStrategy(dates);
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
