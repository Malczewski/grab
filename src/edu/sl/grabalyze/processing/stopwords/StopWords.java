package edu.sl.grabalyze.processing.stopwords;

import java.io.*;
import java.util.*;

public class StopWords {

    private Set<String> words;
    
    public StopWords(String langTag) {
        //ResourceBundle bundle = ResourceBundle.getBundle(
        //        "edu.sl.grabalyze.processing.stopwords.StopWordsBundle",Locale.forLanguageTag(langTag));
        ResourceBundle bundle = null;
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("StopWordsBundle_" + langTag + ".properties"), "Windows-1251"));
            bundle = new PropertyResourceBundle(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
