package edu.sl.grabalyze.processing.stemming;

import java.util.Arrays;

/**
 * http://snowball.tartarus.org/algorithms/russian/stemmer.html
 */
public abstract class AbstractStemmer implements Stemmer{
    
    protected abstract char[] getVowels();
    protected abstract char[] getNonVowels();

    // similar suffixes are sorted by length, i.e. longest are first
    protected abstract String[] getPerfectiveGerund(boolean group1);
    protected abstract String[] getReflexive();
    protected abstract String[] getAdjective();
    protected abstract String[] getParticiple(boolean group1);
    protected abstract String[] getVerb(boolean group1);
    protected abstract String[] getNoun();
    protected abstract String[] getI();
    protected abstract String[] getDerivational();
    protected abstract String[] getSuperlative();
    protected abstract String[] getNN();
    protected abstract String[] getSoftSign();

    /**
     * @param word lower case word
     * @return stemmed word
     */
    @Override
    public String stem(String word) {
        int rv = getRV(word);
        int r2 = getR2(word);
        int endPrev = word.length();
        int endCurr;

        // step 1
        endCurr = searchSuffix(word, rv, endPrev, getPerfectiveGerund(false));
        if (endCurr == -1)
            endCurr = searchSuffix(word, rv, endPrev, getPerfectiveGerund(true), true); // with leading 'a'
        if (endCurr == -1) {
            endCurr = searchSuffix(word, rv, endPrev, getReflexive());
            if (endCurr != -1)
                endPrev = endCurr;
            endCurr = searchSuffix(word, rv, endPrev, getAdjective());
            if (endCurr != -1) {
                int prev = endCurr;
                endCurr = searchSuffix(word, rv, prev, getParticiple(false));
                if (endCurr == -1)
                    endCurr = searchSuffix(word, rv, prev, getParticiple(true), true);
                if (endCurr == -1)
                    endCurr = prev;
            }
            if (endCurr == -1)
                endCurr = searchSuffix(word, rv, endPrev, getVerb(false));
            if (endCurr == -1)
                endCurr = searchSuffix(word, rv, endPrev, getVerb(true), true);
            if (endCurr == -1)
                endCurr = searchSuffix(word, rv, endPrev, getNoun());
            if (endCurr != -1)
                endPrev = endCurr;
        }

        // step 2
        endCurr = searchSuffix(word, rv, endPrev, getI());
        if (endCurr != -1)
            endPrev = endCurr;

        // step 3
        endCurr = searchSuffix(word, r2, endPrev, getDerivational());
        if (endCurr != -1)
            endPrev = endCurr;

        // step 4
        endCurr = searchSuffix(word, rv, endPrev, getSuperlative());
        if (endCurr != -1)
            endPrev = endCurr;
        endCurr = searchSuffix(word, rv, endPrev, getNN(), true);
        if (endCurr != -1)
            endPrev = endCurr;
        endCurr = searchSuffix(word, rv, endPrev, getSoftSign());
        if (endCurr != -1)
            endPrev = endCurr;

        return word.substring(0, endPrev);
    }

    private int getRV(String word) {
        for (int i = 0; i < word.length(); i++)
            if (Arrays.binarySearch(getVowels(), word.charAt(i)) >= 0)
                return i + 1;
        return word.length();
    }

    private int getR2(String word) {
        int prev = -1;
        // R1
        for (int i = getRV(word); i < word.length(); i++)
            if (Arrays.binarySearch(getNonVowels(), word.charAt(i)) >= 0) {
                prev = i;
                break;
            }
        if (prev == -1)
            return word.length();
        boolean found = false;

        for (int i = prev; i < word.length(); i++)
            if (Arrays.binarySearch(getVowels(), word.charAt(i)) >= 0) {
                prev = i;
                found = true;
                break;
            }
        if (!found)
            return word.length();

        found = false;
        for (int i = prev; i < word.length(); i++)
            if (Arrays.binarySearch(getVowels(), word.charAt(i)) >= 0) {
                prev = i;
                found = true;
                break;
            }
        return found ? prev + 1 : word.length();
    }

    private int searchSuffix(String word, int start, int end, String[] suffixes) {
        return searchSuffix(word, start, end, suffixes, false);
    }

    private int searchSuffix(String word, int start, int end, String[] suffixes, boolean exceptLast) {
        if (start > end)
            return -1;
        String w = word.substring(start, end);
        for (String s : suffixes) {
            if (w.endsWith(s))
                return start + w.length() - s.length() + (exceptLast ? 1 : 0);
        }
        return -1;
    }
}
