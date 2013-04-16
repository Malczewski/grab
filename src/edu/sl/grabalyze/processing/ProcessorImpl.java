package edu.sl.grabalyze.processing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sl.grabalyze.entity.Article;

public class ProcessorImpl implements Runnable{

    private List<Article> articles;
    private TextProcessor processor;
    private int id;
    
    private Map<Long, Map<String,Integer>> wordMapping = new HashMap<Long, Map<String,Integer>>(articles.size());

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
    
    public Map<Long, Map<String,Integer>> getMappings() {
        return wordMapping;
    }

    public void run() {
        int count = 0;
        for (Article a : articles) {
            System.out.println("Processor #" + id + " : " + df.format(100.0 * count++ / articles.size()) + "%");
            wordMapping.put(a.getId(), processor.processText(a.getText()));
        }
        System.out.println("Grabber #" + id + " : done!");
    }

    
}
