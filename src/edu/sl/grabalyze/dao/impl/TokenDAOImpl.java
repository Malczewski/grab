package edu.sl.grabalyze.dao.impl;

import java.util.List;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.entity.Token;

public class TokenDAOImpl extends AbstractDAO implements TokenDAO {

    @Override
    public int getMaxToken() {
        //getJdbcTemplate().query()
        return 0;
    }

    @Override
    public List<Token> getTokens() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void batchInsert(List<Token> tokens) {
        // TODO Auto-generated method stub

    }
    

    @Override
    public void saveArticleTokens(int articleId, List<Integer> tokenIds) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Token> getArticleTokens(int articleId) {
        // TODO Auto-generated method stub
        return null;
    }

}
