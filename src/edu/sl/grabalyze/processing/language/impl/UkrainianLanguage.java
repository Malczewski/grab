package edu.sl.grabalyze.processing.language.impl;

import edu.sl.grabalyze.processing.language.Language;
import edu.sl.grabalyze.processing.stemming.Stemmer;
import edu.sl.grabalyze.processing.stemming.impl.UkrainianStemmer;
import edu.sl.grabalyze.processing.stopwords.StopWords;


public class UkrainianLanguage implements Language {

    private StopWords stopWords;
    private Stemmer stemmer;

    public UkrainianLanguage() {
        stopWords = new StopWords("uk");
        stemmer = new UkrainianStemmer();
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