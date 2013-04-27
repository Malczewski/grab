package edu.sl.grabalyze.grabber.strategy;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.gazetaua.GazetaUaFactory;
import edu.sl.grabalyze.grabber.strategy.telegraf.TelegrafFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class GrabberStrategyFactory {
    public abstract GrabberStrategy createListStrategy(List<Date> dates);
    public abstract GrabberStrategy createItemStrategy(Map<Long, String> urls);
    
    public static GrabberStrategyFactory createFactory(String type, ArticleDAO articleDAO) {
        if ("gazetaUa".equalsIgnoreCase(type))
            return new GazetaUaFactory(articleDAO);
        else if ("telegraf".equalsIgnoreCase(type))
            return  new TelegrafFactory(articleDAO);
        throw new IllegalArgumentException("Wrong factory type");
    }
}
