package edu.sl.grabalyze.processing.impl;

import edu.sl.grabalyze.processing.Language;
import edu.sl.grabalyze.processing.Stemmer;
import edu.sl.grabalyze.processing.StopWords;
import edu.sl.grabalyze.processing.impl.stemming.RussianStemmer;



public class RussianLanguage implements Language {

    private StopWords stopWords;
    private Stemmer stemmer;
    
    public RussianLanguage() {
        stopWords = new StopWords("ru");
        stemmer = new RussianStemmer();
    }
    
    @Override
    public StopWords getStopWords() {
        return stopWords;
    }

    @Override
    public Stemmer getStemmer() {
        return stemmer;
    }
    
}