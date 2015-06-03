package org.podcastpedia.common.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.podcastpedia.common.types.UpdateFrequencyType;


@SuppressWarnings("rawtypes")
public class UpdateFrequencyTypeHandler extends BaseTypeHandler {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) throws SQLException {		 
		 ps.setInt(i, ((UpdateFrequencyType) parameter).getCode());		
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return UpdateFrequencyType.get(rs.getInt(columnName));
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {

		return UpdateFrequencyType.get(cs.getInt(columnIndex));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}