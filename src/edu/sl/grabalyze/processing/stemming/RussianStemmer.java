package edu.sl.grabalyze.processing.stemming;

import java.util.Arrays;

public class RussianStemmer extends AbstractStemmer{

    private final char[] vowels = new char[]{'а','е','и','о','у','ы','э','ю','я'};
    private final char[] nonvowels = new char[]{'б','в','г','д','ж','з','й',
            'к','л','м','н','п','р','с','т','ф','х','ц','ч','ш','щ','ъ','ь'};

    private final String[] perf1 = new String[] {"авшись", "явшись", "авши", "явши", "ав", "яв"};
    private final String[] perf2 = new String[] {"ившись", "ывшись", "ивши", "ывши", "ив", "ыв"};
    private final String[] adj = new String[] {"ее", "ие", "ые", "ое", "ими", "ыми", "ей", "ий", 
            "ый", "ой", "ем", "им", "ым", "ом", "его", "ого", "ему", "ому", 
            "их", "ых", "ую", "юю", "ая", "яя", "ою", "ею"};
    private final String[] part1 = new String[] {"аем", "анн", "авш", "ающ", "ащ", "яем", "янн", "явш", "яющ", "ящ"};
    private final String[] part2 = new String[] {"ивш", "ывш", "ующ"};
    private final String[] refl = new String[] {"ся", "сь"};
    private final String[] verb1 = new String[] {"ала", "ана", "аете", "айте", "али", "ай", "ал", "аем", "ан",
            "ало", "ано", "ает", "ают", "аны", "ать", "аешь", "анно",
            "яла", "яна", "яете", "яйте", "яли", "яй", "ял", "яем", "ян",
            "яло", "яно", "яет", "яют", "яны", "ять", "яешь", "янно"};
    private final String[] verb2 = new String[] {"ила", "ыла", "ена", "ейте", "уйте", "ите", "или", "ыли", "ей", 
            "уй", "ил", "ыл", "им", "ым", "ен", "ило", "ыло", "ено", "ят", "ует", "уют", 
            "ит", "ыт", "ены", "ить", "ыть", "ишь", "ую", "ю"};
    private final String[] noun = new String[] {"а", "ев", "ов", "ие", "ье", "е", "иями", "ями", "ами", "еи", "ии",
            "и", "ией", "ей", "ой", "ий", "й", "иям", "ям", "ием", "ем", "ам",
            "ом", "о", "у", "ах", "иях", "ях", "ы", "ь", "ию", "ью", "ю", "ия", "ья", "я"} ;
    private final String[] sup = new String[] {"ейш", "ейше"};
    private final String[] deriv = new String[] {"ост", "ость"};
    private final String[] nn = new String[] {"нн"};
    private final String[] soft = new String[] {"ь"};
    private final String[] i = new String[] {"и"};
    
    public RussianStemmer() {
        Arrays.sort(vowels);
        Arrays.sort(nonvowels);
    }

    @Override
    protected char[] getVowels() {
        return vowels;
    }

    @Override
    protected char[] getNonVowels() {
        return nonvowels;
    }

    @Override
    protected String[] getPerfectiveGerund(boolean group1) {
        return group1 ? perf1 : perf2;
    }

    @Override
    protected String[] getReflexive() {
        return refl;
    }

    @Override
    protected String[] getAdjective() {
        return adj;
    }

    @Override
    protected String[] getParticiple(boolean group1) {
        return group1 ? part1 : part2;
    }

    @Override
    protected String[] getVerb(boolean group1) {
        return group1 ? verb1 : verb2;
    }

    @Override
    protected String[] getNoun() {
        return noun;
    }

    @Override
    protected String[] getI() {
        return i;
    }

    @Override
    protected String[] getDerivational() {
        return deriv;
    }

    @Override
    protected String[] getSuperlative() {
        return sup;
    }

    @Override
    protected String[] getNN() {
        return nn;
    }

    @Override
    protected String[] getSoftSign() {
        return soft;
    }

}
