package edu.sl.grabalyze.dao;

import edu.sl.grabalyze.entity.Article;

import java.util.Date;
import java.util.List;


public interface ArticleDAO {

    void batchInsert(List<Article> list);
    
    void updateContent(long id, String text);

    List<Article> getArticles(int count);
    List<Article> getNotProcessedArticles(int count, Date beforeDate);
}
