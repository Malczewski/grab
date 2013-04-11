package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.grabber.Grabber;
import edu.sl.grabalyze.grabber.GrabberImpl;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

import java.util.ArrayList;
import java.util.List;

public class GrabberFactory {

    List<GrabberStrategy> strategies;

    public void setStrategies(List<GrabberStrategy> strategies) {
        this.strategies = new ArrayList<>(strategies);
    }

    public List<Grabber> createGrabbers() {
        List<Grabber> result = new ArrayList<>(strategies.size());
        int id = 0;
        for (GrabberStrategy strategy : strategies) {
            GrabberImpl g = new GrabberImpl();
            g.setId(++id);
            g.setStrategy(strategy);
            result.add(g);
        }
        return result;
    }

}
