package edu.sl.grabalyze.execution;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgramExecutor {
    private RunnableFactory factory;
    private int threadCount;
    
    public void setFactory(RunnableFactory factory) {
        this.factory = factory;
    }
    
    public void setThreadCount(int count) {
        this.threadCount = count;
    }

    public void execute() {
        List<Runnable> runnables = factory.create(threadCount);
        if (runnables.size() == 0)
            return;
        System.out.println("Start executing.");
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(runnables.size());
        for (Runnable grabber : runnables) {
            executor.execute(grabber);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Execution finished in " + (System.currentTimeMillis() - start)/1000 + "s");
        
        factory.createCallback().onSuccess(runnables);
        System.out.println("End executing. Total time=" + (System.currentTimeMillis() - start)/1000 + "s");
    }
}
