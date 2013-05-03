package edu.sl.grabalyze.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.entity.Token;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class TokenDAOImpl extends AbstractDAO implements TokenDAO {


    @Override
    public List<Token> getTokens() {
        return getJdbcTemplate().query("SELECT token_id, word FROM tokens ORDER BY token_id", new RowMapper<Token>() {
            @Override
            public Token mapRow(ResultSet resultSet, int i) throws SQLException {
                Token token = new Token();
                token.setId(resultSet.getLong(1));
                token.setWord(resultSet.getString(2));
                return token;
            }
        });
    }

    @Override
    public void batchInsert(Collection<Token> tokens) {
        getJdbcTemplate().batchUpdate("INSERT INTO tokens(token_id, word) VALUES (?, ?)",
                tokens, 1000, new ParameterizedPreparedStatementSetter<Token>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, Token token) throws SQLException {
                preparedStatement.setLong(1, token.getId());
                preparedStatement.setString(2, token.getWord());
            }
        });

    }
    

    @Override
    public void saveArticleTokens(final long articleId, Map<Long, Integer> tokenCounts) {
        getJdbcTemplate().batchUpdate("INSERT INTO article_tokens (article_id, token_id, token_count) VALUES (?, ?, ?)",
                tokenCounts.entrySet(), 1000,
                new ParameterizedPreparedStatementSetter<Map.Entry<Long, Integer>>() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement,
                                          Map.Entry<Long, Integer> longIntegerEntry) throws SQLException {
                        preparedStatement.setLong(1, articleId);
                        preparedStatement.setLong(2, longIntegerEntry.getKey());
                        preparedStatement.setInt(3, longIntegerEntry.getValue());
                    }
                });
    }

    @Override
    public List<Token> getArticlesTokens(long articleId) {
        return getJdbcTemplate().query("SELECT t.token_id, t.word FROM article_tokens at JOIN tokens t ON at.token_id = t.token_id" +
                " WHERE article_id = ? ORDER BY t.token_id", new Object[] {articleId}, new RowMapper<Token>() {
            @Override
            public Token mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Token(resultSet.getLong(1), resultSet.getString(2));
            }
        });
    }

    @Override
    public void clearArticleTokens(Collection<Article> articles) {
        getJdbcTemplate().batchUpdate("DELETE FROM article_tokens WHERE article_id = ?",
                articles, 1000, new ParameterizedPreparedStatementSetter<Article>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, Article article) throws SQLException {
                preparedStatement.setLong(1, article.getId());
            }
        });
    }

}
