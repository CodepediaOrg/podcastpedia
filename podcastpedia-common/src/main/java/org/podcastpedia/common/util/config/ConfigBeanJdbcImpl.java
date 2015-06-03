package org.podcastpedia.common.util.config;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Spring JdbcTemplate implemenation of the ConfigBean interface
 * 
 * @author ama
 *
 */
public class ConfigBeanJdbcImpl implements ConfigBean {

    /** The jdbc template. */
    private JdbcTemplate jdbcTemplate;
   
    /**
     * Sets the data source.
     *
     * @param datasource the new data source
     */
    public void setDataSource(DataSource datasource) {
            this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    /**
     * Returns the value associated with the property from the database
     *
     * @param property the property
     * @return the string
     */
    public String get(String property) {
            String select =                
            		"SELECT value FROM config_data WHERE PROPERTY = '" + property + "'";
            return jdbcTemplate.query(select, new StringResultSetExtractor());
    } 

}
