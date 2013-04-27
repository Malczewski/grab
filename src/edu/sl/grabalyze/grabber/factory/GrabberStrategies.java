package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategyFactory;

import java.util.List;

public abstract class GrabberStrategies {

    private GrabberStrategyFactory strategyFactory;

    public void setStrategyFactory(GrabberStrategyFactory factory) {
        this.strategyFactory = factory;
    }

    public GrabberStrategyFactory getStrategyFactory() {
        return this.strategyFactory;
    }

    public abstract List<GrabberStrategy> createStrategies(int threads);
}
