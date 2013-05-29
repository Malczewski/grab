package edu.sl.grabalyze.processing.post;

import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.processing.ProcessorImpl;
import edu.sl.grabalyze.utils.ProgressMonitor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class TextToFilePostProcessor implements PostProcessor {

    private String filename;
    
    public TextToFilePostProcessor() {
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void onSuccess(List<Runnable> result) {
        System.out.println("Post processing started.");

        Set<Article> total = new HashSet<>(10000);
        
        for (Runnable proc : result) {
             total.addAll(((ProcessorImpl) proc).getMappings().keySet());
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        ProgressMonitor monitor = new ProgressMonitor(total.size());
        System.out.println("Start processing articles:");
        for (Article article : total) {
            writer.println(article.getCategoryName() + ":" + article.getText());
            monitor.increment();
        }

        writer.flush();
        writer.close();
        System.out.println("Processing finished successfully.");
    }
    
}
