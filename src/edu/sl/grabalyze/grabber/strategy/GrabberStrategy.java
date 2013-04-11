package edu.sl.grabalyze.grabber.strategy;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 05.04.13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public interface GrabberStrategy {

    void processHtml(String html);

    String nextUrl();

    boolean hasUrl();

    double getProgress();

}
