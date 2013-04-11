package edu.sl.grabalyze.main;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class LoadController {
    private static LoadController instance;

    private DelayQueue<FixedDelayed> queue;

    private long delay;
    
    private LoadController() {
        queue = new DelayQueue<>();
        queue.offer(new FixedDelayed());
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public static LoadController get() {
        if (instance == null)
            instance = new LoadController();
        return instance;
    }
    
    public synchronized void takeControl() {
        FixedDelayed d = null;
        try {
            d = queue.take();
            d.reset();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.offer(d);
    }

    private class FixedDelayed implements Delayed {

        private long lastTime = System.currentTimeMillis();
        
        @Override
        public long getDelay(TimeUnit unit) {
            long diffMillis = lastTime + delay - System.currentTimeMillis();
            return unit.convert(diffMillis, TimeUnit.MILLISECONDS);
        }
        
        public void reset() {
            lastTime = System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
