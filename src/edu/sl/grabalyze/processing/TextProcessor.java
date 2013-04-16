package edu.sl.grabalyze.processing;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.sl.grabalyze.processing.language.Language;

public class TextProcessor {

    private Language language;

    
    public TextProcessor(Language language) {
        this.language = language;
    }
    
    /**
     * @param text input text
     * @return each unique word and its frequency
     */
    public Map<String, Integer> processText(String text) {
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        String text1 = text.replace(',',' ').replace('.', ' ').replace(':', ' ')
                .replace(';',' ').replace('?', ' ').replace('!', ' ')
                .replace('[',' ').replace(']', ' ').replace('(', ' ')
                .replace(')',' ').replace('-',' ');
        String[] words = text1.split("\\s+");
        String word;
        String stemWord;
        for (int i = 0; i < words.length; i++) {
            word = words[i].toLowerCase();
            if (word.charAt(0) != '\"' && word.charAt(word.length() - 1) == '\"')
                word = word.replace("\"", "");
            if (language.getStopWords().isStopWord(word))
                continue;
            stemWord = language.getStemmer().stem(word).replace("\"", "").replace("'", "");
            result.put(stemWord, 1 + (result.containsKey(stemWord) ? result.get(stemWord) : 0));
        }
        return result;
    }
}
