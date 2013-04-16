package edu.sl.grabalyze.dao.impl;


import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.entity.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleDAOImpl extends AbstractDAO implements ArticleDAO {

    @Override
    public void batchInsert(List<Article> list) {
        getJdbcTemplate().batchUpdate("INSERT INTO articles (article_id, url, title, category_code, category_name, content, doc_date)" +
                " values (?,?,?,?,?,?,?)",list, 1000, new ParameterizedPreparedStatementSetter<Article>(){
            @Override
            public void setValues(PreparedStatement preparedStatement, Article article) throws SQLException {
                preparedStatement.setLong(1, article.getId());
                preparedStatement.setString(2, article.getUrl());
                preparedStatement.setString(3, article.getTitle());
                preparedStatement.setString(4, article.getCategoryCode());
                preparedStatement.setString(5, article.getCategoryName());
                preparedStatement.setString(6, article.getText());
                preparedStatement.setDate(7, new Date(article.getDate().getTime()));
            }
        });
    }
    
    @Override
    public void updateContent(long id, String text) {
        getJdbcTemplate().update("UPDATE articles SET content = ? WHERE article_id = ?", text, id);
    }

    @Override
    public List<Article> getArticles(int count) {
        return getJdbcTemplate().query("SELECT article_id, url, title, category_code, category_name, content, doc_date FROM articles" +
        		" ORDER BY doc_date desc LIMIT ?", new Object[] {count}, new ArticleMapper());
    }

    @Override
    public List<Article> getNotProcessedArticles(int count, java.util.Date beforeDate) {
        return getJdbcTemplate().query("SELECT article_id, url, title, category_code, category_name, content, doc_date FROM articles" +
                " WHERE content is null and doc_date < ? ORDER BY doc_date desc LIMIT ?", new Object[] { beforeDate, count },
                new ArticleMapper());
    }

    private class ArticleMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            Article art = new Article();
            int k = 1;
            art.setId(resultSet.getLong(k++));
            art.setUrl(resultSet.getString(k++));
            art.setTitle(resultSet.getString(k++));
            art.setCategoryCode(resultSet.getString(k++));
            art.setCategoryName(resultSet.getString(k++));
            art.setText(resultSet.getString(k++));
            art.setDate(resultSet.getDate(k++));
            return art;
        }
    }
}
