package edu.sl.grabalyze.processing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.utils.ProgressMonitor;

public class ProcessorImpl implements Runnable{

    private List<Article> articles;
    private TextProcessor processor;
    private int id;
    
    private Map<Article, Map<String,Integer>> wordMapping = new HashMap<Article, Map<String,Integer>>(1000);

    private final DecimalFormat df = new DecimalFormat("#.##");

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
    
    public void setTextProcessor(TextProcessor processor) {
        this.processor = processor;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Map<Article, Map<String,Integer>> getMappings() {
        return wordMapping;
    }

    public void run() {
        ProgressMonitor mon = new ProgressMonitor(articles.size(), "Processor #" + id + " : ");
        for (Article a : articles) {
            wordMapping.put(a, processor.processText(a.getText()));
            mon.increment();
        }
        System.out.println("Processor #" + id + " : done!");
    }

    
}
