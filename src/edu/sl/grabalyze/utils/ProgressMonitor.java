package edu.sl.grabalyze.utils;

public class ProgressMonitor {
    
    private int counter, last;
    private int size;
    
    private String text;
    
    public ProgressMonitor(int size) {
        this.size = size;
        counter = 0;
        last = 0;
        text = null;
    }
    
    public ProgressMonitor(int size, String text) {
        this(size);
        this.text = text;
    }
    
    public void increment() {
        counter++;
        if ((counter * 100 / size) / 5 != last) {
            last = (counter * 100 / size) / 5;
            System.out.println((text == null ? "Processed: " : text) + (counter * 100 / size) + "%");
        }
    }
}
