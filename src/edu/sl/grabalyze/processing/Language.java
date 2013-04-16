package edu.sl.grabalyze.processing;

public interface Language {
    StopWords getStopWords();
    Stemmer getStemmer();
}
