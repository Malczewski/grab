package edu.sl.grabalyze.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.entity.Token;

public interface TokenDAO {

    List<Token> getTokens();
    
    void batchInsert(Collection<Token> tokens);
    
    void saveArticleTokens(long articleId, Map<Long, Integer> tokenCounts);
    List<Token> getArticlesTokens(long articleId);
    void clearArticleTokens(Collection<Article> articles);
}
