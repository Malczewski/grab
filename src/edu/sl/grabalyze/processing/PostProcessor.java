package edu.sl.grabalyze.processing;

import java.util.List;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.execution.Callback;

public class PostProcessor implements Callback<List<Runnable>> {

    private TokenDAO tokenDAO;
    
    public PostProcessor(TokenDAO dao) {
        this.tokenDAO = dao;
    }
    
    @Override
    public void onSuccess(List<Runnable> result) {
        
    }

}
