package edu.sl.grabalyze.grabber.strategy;

public interface GrabberStrategy {

    void processHtml(String html);

    String nextUrl();

    boolean hasUrl();

    double getProgress();

}
