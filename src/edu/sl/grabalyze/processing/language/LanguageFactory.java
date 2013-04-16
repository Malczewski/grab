package edu.sl.grabalyze.processing.language;

import edu.sl.grabalyze.processing.language.impl.*;

public class LanguageFactory {

    private LanguageFactory(){};
    
    public static Language createRussianLanguage() {
        return new RussianLanguage();
    }
}
