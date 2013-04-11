package edu.sl.grabalyze.grabber.factory;

import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

import java.util.List;

public interface GrabberStrategyFactory {
    List<GrabberStrategy> createStrategies(int count);
}
