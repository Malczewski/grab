package edu.sl.grabalyze.entity;

public class Token {
    private long id;
    private String word;
    
    public Token() {}
    
    public Token(long id, String word) {
        this.id = id;
        this.word = word;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    
}
