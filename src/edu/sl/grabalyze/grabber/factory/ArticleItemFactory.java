package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.grabber.strategy.gazetaua.GazetaUaArticleStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

import java.util.*;

public class ArticleItemFactory extends GrabberStrategies {

    private ArticleDAO articleDAO;
    private int countPerWorker;
    private int offset;

    public ArticleItemFactory(int countPerWorker, int offset) {
        this.countPerWorker = countPerWorker;
        this.offset = offset;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<GrabberStrategy> createStrategies(int count) {
        System.out.println("Requesting for article urls");
        List<GrabberStrategy> result = new ArrayList<>(count);
        List<Article> articles = articleDAO.getNotProcessedArticles(count * countPerWorker, offset);
        for (int i = 0; i < count; i++) {
            HashMap<Long, String> map = new HashMap<>(countPerWorker);
            for (int j = 0; j < countPerWorker && j + i * countPerWorker < articles.size(); j++) {
                Article a = articles.get(j + i * countPerWorker);
                map.put(a.getId(), a.getUrl());
            }
            GrabberStrategy strategy = getStrategyFactory().createItemStrategy(map);
            result.add(strategy);
        }
        System.out.println("Got " + articles.size() + " articles for " + count + " workers.");
        if (articles.size() > 0)
            System.out.println("Dates:" + articles.get(0).getDate() + " to " + articles.get(articles.size()-1).getDate());
        return result;
    }
}
