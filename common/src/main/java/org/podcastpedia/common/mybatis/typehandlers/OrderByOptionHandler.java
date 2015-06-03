package org.podcastpedia.common.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.podcastpedia.common.types.OrderByOption;


@SuppressWarnings("rawtypes")
public class OrderByOptionHandler  extends BaseTypeHandler{

	@Override
	/**
	 * This is used for insertion
	 */
	public void setNonNullParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) throws SQLException {		 
		 ps.setString(i, ((OrderByOption) parameter).getCode());
	}

	@Override
	/**
	 * This is used by select statements 
	 */
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return OrderByOption.get(rs.getString(columnName));
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return OrderByOption.get(cs.getString(columnIndex));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
