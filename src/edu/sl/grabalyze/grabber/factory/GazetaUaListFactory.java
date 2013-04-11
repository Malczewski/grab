package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GazetaUaListStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GazetaUaListFactory implements GrabberStrategyFactory {

    private Date dateTo, dateFrom;
    private ArticleDAO articleDAO;

    private static final long DAY = 1000 * 60 * 60 * 24;

    public GazetaUaListFactory(Date from, Date to) {
        this.dateFrom = new Date(from.getTime());
        this.dateTo = new Date(to.getTime());
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<GrabberStrategy> createStrategies(int count) {
        List<GrabberStrategy> result = new ArrayList<>(count);
        if (dateFrom.after(dateTo)) {
            System.out.println("From date is after to date:" + dateFrom + " - " + dateTo);
            return result;
        }
        Date from = new Date(dateFrom.getTime());
        int days = (int) ((dateTo.getTime() - dateFrom.getTime()) / DAY / count);
        while (!from.after(dateTo)) {
            Date next = new Date(from.getTime() + days * DAY);
            if (next.after(dateTo))
                next.setTime(dateTo.getTime());
            GazetaUaListStrategy str = new GazetaUaListStrategy(from, next);
            str.setArticleDAO(articleDAO);
            result.add(str);
            from.setTime(next.getTime() + DAY);
        }

        return result;
    }
}
