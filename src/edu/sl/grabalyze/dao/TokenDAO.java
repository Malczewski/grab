package edu.sl.grabalyze.dao;

import java.util.List;

import edu.sl.grabalyze.entity.Token;

public interface TokenDAO {

    int getMaxToken();
    List<Token> getTokens();
    
    void batchInsert(List<Token> tokens);
    
    void saveArticleTokens(int articleId, List<Integer> tokenIds);
    List<Token> getArticleTokens(int articleId);
}
