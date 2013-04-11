package edu.sl.grabalyze.main;


import edu.sl.grabalyze.grabber.Grabber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GrabberExecutor {
    List<Grabber> grabbers = new ArrayList<>();

    public void setGrabbers(List<Grabber> list) {
        grabbers = new ArrayList<>(list);
    }

    public void execute() {
        if (grabbers.size() == 0)
            return;
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(grabbers.size());
        for (Grabber grabber : grabbers) {
            executor.execute(new GrabberRunnable(grabber));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished in " + (System.currentTimeMillis() - start) + "ms");
    }

    private class GrabberRunnable implements Runnable {
        private Grabber grabber;

        public GrabberRunnable(Grabber grabber) {
            this.grabber = grabber;
        }

        public void run() {
            grabber.process();
        }
    }
}
