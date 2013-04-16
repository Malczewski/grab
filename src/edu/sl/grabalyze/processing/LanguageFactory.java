package edu.sl.grabalyze.processing;

import edu.sl.grabalyze.processing.impl.*;

public class LanguageFactory {

    private LanguageFactory(){};
    
    public static Language createRussianLanguage() {
        return new RussianLanguage();
    }
}
