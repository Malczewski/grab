package edu.sl.grabalyze.processing.language;

import edu.sl.grabalyze.processing.language.impl.*;

public class LanguageFactory {

    private LanguageFactory(){};
    
    public static Language createLanguage(String langTag) {
        if ("ru".equals(langTag))
            return new RussianLanguage();
        else if ("uk".equals(langTag))
            return new UkrainianLanguage();
        throw new UnsupportedOperationException("Unsupported language");
    }
}
