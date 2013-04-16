package edu.sl.grabalyze.processing.language;

import edu.sl.grabalyze.processing.stemming.Stemmer;
import edu.sl.grabalyze.processing.stopwords.StopWords;

public interface Language {
    StopWords getStopWords();
    Stemmer getStemmer();
}
