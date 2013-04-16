package edu.sl.grabalyze.processing.language.impl;

import edu.sl.grabalyze.processing.language.Language;
import edu.sl.grabalyze.processing.stemming.Stemmer;
import edu.sl.grabalyze.processing.stemming.impl.RussianStemmer;
import edu.sl.grabalyze.processing.stopwords.StopWords;



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