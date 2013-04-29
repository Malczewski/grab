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
    
    private Map<Long, Map<String,Integer>> wordMapping = new HashMap<Long, Map<String,Integer>>(1000);

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
        int counter = 0, last = 0;
        int size = articles.size();
        for (Article a : articles) {
            wordMapping.put(a.getId(), processor.processText(a.getText()));
            counter++;
            if ((counter * 100 / size) / 5 != last) {
                last = (counter * 100 / size) / 5;
                System.out.println("Processor #" + id + " : " + (counter * 100 / size) + "%");
            }
        }
        System.out.println("Processor #" + id + " : done!");
    }

    
}
