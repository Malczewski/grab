package edu.sl.grabalyze.dao;


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

public class ArticleDAOImpl implements ArticleDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Override
    public void batchInsert(List<Article> list) {
        getJdbcTemplate().batchUpdate("INSERT INTO articles (id, url, title, category_code, category_name, content, doc_date)" +
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
    public void batchUpdate(List<Article> list) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    @Override
    public void updateContent(long id, String text) {
        getJdbcTemplate().update("UPDATE articles SET content = ? WHERE id = ?", text, id);
    }

    @Override
    public List<Article> getArticles() {
        return getJdbcTemplate().query("SELECT id, url, title, category_code, category_name, content, doc_date FROM articles",
                new ArticleMapper());
    }

    @Override
    public List<Article> getNotProcessedArticles(int count, java.util.Date beforeDate) {
        return getJdbcTemplate().query("SELECT id, url, title, category_code, category_name, content, doc_date FROM articles" +
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
