package edu.sl.grabalyze.dao.impl;


import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.entity.Article;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.io.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileArticleDAOImpl implements ArticleDAO {

    private String filename;
    
    public FileArticleDAOImpl(){}
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void batchInsert(List<Article> list) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void updateContent(long id, String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Article> getArticles(int count, int offset) {
        List<Article> result = new ArrayList<>(count);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String line;
        try {
            int current = 0;
            while ((line = br.readLine()) != null) {
                if (current++ < offset)
                    continue;
                if (current - offset > count)
                    break;
                Article a = new Article();
                String[] words = line.split(":", 2);
                a.setCategoryName(words[0]);
                a.setText(words[1]);
                a.setId(current);
                result.add(a);
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Article> getNotProcessedArticles(int count, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<java.util.Date> getNotProcessedDays(java.util.Date start, java.util.Date end) {
        throw new UnsupportedOperationException();
    }
}
