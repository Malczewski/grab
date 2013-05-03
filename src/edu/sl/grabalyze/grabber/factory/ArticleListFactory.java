package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.factory.util.Distributor;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleListFactory extends GrabberStrategies {

    private Date dateTo, dateFrom;
    @Autowired
    private ArticleDAO articleDAO;

    public ArticleListFactory(Date from, Date to) {
        this.dateFrom = new Date(from.getTime());
        this.dateTo = new Date(to.getTime());
    }

    public List<GrabberStrategy> createStrategies(int threads) {
        System.out.println("Requesting for list urls.");
        List<GrabberStrategy> result = new ArrayList<>(threads);
        if (dateFrom.after(dateTo)) {
            System.out.println("From date is after to date:" + dateFrom + " - " + dateTo);
            return result;
        }
        List<Date> dates = articleDAO.getNotProcessedDays(dateFrom, dateTo);
        System.out.println("Got " + dates.size() + " dates for " + threads + " workers.");
        List<List<Date>> distr = new Distributor<Date>(dates).distribute(threads);
        for (List<Date> list : distr) {
            GrabberStrategy str = getStrategyFactory().createListStrategy(list);
            result.add(str);
        }

        return result;
    }
}
