package edu.sl.grabalyze.dao;

import edu.sl.grabalyze.entity.Article;

import java.util.Date;
import java.util.List;


public interface ArticleDAO {

    void batchInsert(List<Article> list);
    
    void updateContent(long id, String text);

    void remove(long id);

    List<Article> getArticles(int count, int offset);
    List<Article> getNotProcessedArticles(int count, int offset);
}
