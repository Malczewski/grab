package edu.sl.grabalyze.grabber;

import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.main.LoadController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class GrabberImpl implements Grabber {

    private GrabberStrategy strategy;
    private int id;

    private final DecimalFormat df = new DecimalFormat("#.##");

    public void setStrategy(GrabberStrategy strategy) {
        this.strategy = strategy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void process() {
        while (strategy.hasUrl()) {
            LoadController.get().takeControl();
            String html = requestHttp(strategy.nextUrl());
            try {
                strategy.processHtml(html);
                System.out.println("Grabber #" + id + " : " + df.format(strategy.getProgress() * 100) + "%");
            } catch (Exception ex) {
                System.err.println("Error in :\n" + html);
                System.err.println(ex);
            }
        }
        System.out.println("Grabber #" + id + " : done!");
    }

    private String requestHttp(String urlInput) {
        //System.out.println("Processing " + urlInput);
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlInput);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF8"));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return result;
    }
}
