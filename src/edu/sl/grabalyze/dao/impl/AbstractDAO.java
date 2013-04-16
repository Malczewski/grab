package edu.sl.grabalyze.dao.impl;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
