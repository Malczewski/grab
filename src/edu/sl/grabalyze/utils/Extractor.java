package edu.sl.grabalyze.utils;

/**
 * User: Admin
 * Date: 26.04.13
 * Time: 23:49
 */
public class Extractor {
    private String prefix, suffix;
    private int index;

    public void setString(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String extractValue(String html) {
        int start = html.indexOf(prefix, index) + prefix.length();
        if (start == prefix.length() - 1)
            return "";
        int end = html.indexOf(suffix, start);
        index = end + suffix.length();
        return html.substring(start, end);
    }
}
