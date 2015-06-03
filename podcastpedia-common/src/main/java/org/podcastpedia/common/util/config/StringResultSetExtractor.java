package org.podcastpedia.common.util.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Helper class for the ConfigBeanJdbcImpl implementation @see {@link org.podcastpedia.common.util.config.ConfigBeanJdbcImpl#get(String)} 
 * 
 * @author ama
 *
 */
public class StringResultSetExtractor implements ResultSetExtractor<String> {

	public String extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		//first position the cursor to the first row 
		rs.next();
		return rs.getString(1);
	}

}
