package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleListFactory extends GrabberStrategies {

    private Date dateTo, dateFrom;

    private static final long DAY = 1000 * 60 * 60 * 24;

    public ArticleListFactory(Date from, Date to) {
        this.dateFrom = new Date(from.getTime());
        this.dateTo = new Date(to.getTime());
    }

    public List<GrabberStrategy> createStrategies(int count) {
        System.out.println("Requesting for list urls.");
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
            GrabberStrategy str = getStrategyFactory().createListStrategy(from, next);
            result.add(str);
            from.setTime(next.getTime() + DAY);
        }

        return result;
    }
}
