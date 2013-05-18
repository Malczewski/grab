package edu.sl.grabalyze.processing.post;

import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.processing.ProcessorImpl;
import edu.sl.grabalyze.utils.ProgressMonitor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class FilePostProcessor implements PostProcessor {

    // file extensions
    private static final String TRAIN = "train";
    private static final String TEST = "test";
    private static final String CATEGORIES = "categories";
    private static final String WORDS = "words";
    private static final String NAMES = "names";
    
    private int minThreshold = 0;
    private int maxThreshold = Integer.MAX_VALUE;
    private boolean c45format;
    private int trainSize;
    private String seed;
    private String filename;
    
    public FilePostProcessor() {
    }
    
    public void setMinThreshold(int threshold) {
        this.minThreshold = threshold;
    }

    public void setMaxThreshold(int threshold) {
        this.maxThreshold = threshold;
    }
    
    public void setC45format(boolean c45format) {
        this.c45format = c45format;
    }

    public void setTrainSize(int trainSize) {
        this.trainSize = trainSize;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void onSuccess(List<Runnable> result) {
        System.out.println("Post processing started.");
       
        HashMap<String, Integer> tokenIds = new HashMap<>(1000);
        HashMap<String, Integer> tokenTotalCounts = new HashMap<>(1000);
        Map<Article, Map<String, Integer>> total = new HashMap<>(1000);
        
        for (Runnable proc : result) {
             total.putAll(((ProcessorImpl)proc).getMappings());
        }
        
        Map<Article, Map<Integer, Integer>> articles = new HashMap<>(total.size());

        ProgressMonitor monitor = new ProgressMonitor(total.size());
        System.out.println("Start processing counts(" + total.size() + " articles):");
        for (Article article : total.keySet()) {
            Map<String, Integer> counts = total.get(article);
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                Integer prevCount = tokenTotalCounts.get(entry.getKey());
                tokenTotalCounts.put(entry.getKey(), entry.getValue() + (prevCount == null ? 0 : prevCount));
            }
            monitor.increment();
        }

        int skipped = 0;
        int maxToken = 0;
        monitor = new ProgressMonitor(total.size());
        System.out.println("Start processing tokens(" + total.size() + " articles):");
        for (Article article : total.keySet()) {
            Map<String, Integer> counts = total.get(article);
            Map<Integer, Integer> tokenCounts = new HashMap<>(counts.size());
            Integer tokenId;
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                tokenId = tokenIds.get(entry.getKey());
                if (tokenId == null) {
                    int cnt = tokenTotalCounts.get(entry.getKey());
                    if (cnt < minThreshold || cnt > maxThreshold) {
                        skipped++;
                        continue;
                    }
                    tokenId = ++maxToken;
                    tokenIds.put(entry.getKey(), tokenId);
                }
                tokenCounts.put(tokenId, entry.getValue());
            }
            articles.put(article, tokenCounts);
            monitor.increment();
        }

        System.out.println("Saving counts.");
        try {
            saveToFile(articles, tokenIds, tokenIds.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Post processing finished.");
        System.out.println("Total words: " + tokenTotalCounts.size()
            + "; active words: " + maxToken + "; skipped: " + skipped);
    }
    
    private void saveTokens(String[] words) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename + "." + WORDS);
        for (int i = 0; i < words.length; i++)
            writer.println((i + 1) + "=" + words[i]);
        writer.flush();
        writer.close();
    }

    private void saveCategories(Map<Integer, List<Article>> categoryArticles) throws FileNotFoundException {
        System.out.println("Saving " + categoryArticles.size() + " categories.");
        PrintWriter writer = new PrintWriter(filename + "." + CATEGORIES);
        for (int i = 0; i < categoryArticles.size(); i++) {
            List<Article> list = categoryArticles.get(Integer.valueOf(i));
            writer.println((i + 1) + "=" + list.get(0).getCategoryName() + " (" + list.size() + ")");
        }
        writer.flush();
        writer.close();
    }
    
    /*private void saveNamesFile(String[] words, Map<Integer,
            List<Article>> categoryArticles) throws FileNotFoundException {
        System.out.println("Saving " + categoryArticles.size() + " categories.");
        PrintWriter writer = new PrintWriter(filename + "." + NAMES);
        for (int i = 0; i < categoryArticles.size(); i++) {
            List<Article> list = categoryArticles.get(Integer.valueOf(i));
            writer.print(list.get(0).getCategoryName() + (i == (categoryArticles.size() - 1) ? "" : ","));
        }
        writer.println();
        for (int i = 0; i < words.length; i++)
            writer.println((i + 1) + ": discrete300");
        writer.flush();
        writer.close();
    }*/

    private void saveToFile(Map<Article, Map<Integer, Integer>> articles,
                            Map<String, Integer> tokenIds,
                            int tokenCount) throws FileNotFoundException {
        Map<String, Integer> categoryIds = new HashMap<>();
        Map<Integer, List<Article>> categoryArticles = new HashMap<>();
        int currId = 0;
        for (Article a : articles.keySet()) {
            Integer id = categoryIds.get(a.getCategoryName());
            if (id == null) {
                id = currId++;
                categoryIds.put(a.getCategoryName(), id);
                categoryArticles.put(id, new ArrayList<Article>());
            }
            categoryArticles.get(id).add(a);
        }
        
        String[] words = new String[tokenIds.size()];
        for (String s : tokenIds.keySet())
            words[tokenIds.get(s) - 1] = s;

        PrintWriter train = new PrintWriter(filename + "." + TRAIN);
        PrintWriter test = new PrintWriter(filename + "." + TEST);
        Random rand = new Random(seed.hashCode());

        //if (!c45format) {
            saveTokens(words);
            saveCategories(categoryArticles);
        //} else {
            //saveNamesFile(words, categoryArticles);
            /*for (int i = 0; i < words.length; i++) {
                train.print(words[i] + ',');
                test.print(words[i] + ',');
            }
            train.println("class");
            test.println("class");     */
        //}

        ProgressMonitor monitor = new ProgressMonitor(articles.size());
        for (Integer id : categoryArticles.keySet()) {
            List<Article> list = new ArrayList<>(categoryArticles.get(id));
            Collections.shuffle(list, rand);
            int counter = 0;
            int trainCount = trainSize * list.size() / 100;
            for (Article a : list) {
                PrintWriter writer = (counter++ < trainCount) ? train : test;
                if (!c45format)
                    writer.print(categoryIds.get(a.getCategoryName()));
                Map<Integer, Integer> counts = articles.get(a);
                for (int i = 1; i <= tokenCount; i++) {
                    Integer count = counts.get(Integer.valueOf(i));
                    if (c45format) { // all values, with zeros 
                        writer.print(count == null ? 0 : count);
                        writer.print(',');
                    } else {   // sparse format, only non-zero values
                        if (count != null) {
                            writer.print(' ');
                            writer.print(i + ":" + count);
                        }
                    }
                }
                if (c45format)
                    writer.print(categoryIds.get(a.getCategoryName()));
                writer.println();
                monitor.increment();
            }
        }
        train.flush();
        test.flush();
        train.close();
        test.close();
    }
    
}
