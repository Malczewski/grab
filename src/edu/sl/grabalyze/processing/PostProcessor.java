package edu.sl.grabalyze.processing;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import edu.sl.grabalyze.dao.TokenDAO;
import edu.sl.grabalyze.execution.Callback;

public class PostProcessor implements Callback<List<Runnable>> {

    private TokenDAO tokenDAO;
    
    public PostProcessor(TokenDAO dao) {
        this.tokenDAO = dao;
    }
    
    @Override
    public void onSuccess(List<Runnable> result) {
        HashSet<String> words = new LinkedHashSet<String>(1000);
        for (Runnable proc : result) {
            for (Map<String, Integer> map : ((ProcessorImpl)proc).getMappings().values())
                words.addAll(map.keySet());
        }
        System.out.println(words);
    }

}
