package edu.sl.grabalyze.processing;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class StopWords {

    private Set<String> words;
    
    public StopWords(String langTag) {
        ResourceBundle bundle = ResourceBundle.getBundle("edu.sl.grabalyze.processing.impl.stopwords.StopWordsBundle",Locale.forLanguageTag(langTag));
        words = new HashSet<String>(100);
        Enumeration<String> en = bundle.getKeys();
        while (en.hasMoreElements())
            words.add(en.nextElement());
    }
    
    public boolean isStopWord(String word) {
        if (word.length() <= 1)
            return true;
        boolean digits = true;
        for (int i = 0; i < word.length(); i++)
            if (Character.isAlphabetic(word.charAt(i))){
                digits = false;
                break;
            }
        if (digits)
            return true;
        if (words.contains(word))
            return true;
        return false;
    }
}
