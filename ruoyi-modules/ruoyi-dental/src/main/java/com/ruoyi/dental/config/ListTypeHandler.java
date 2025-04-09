package com.ruoyi.dental.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR) // 根据实际数据库类型调整
@MappedTypes(List.class)
public class ListTypeHandler extends BaseTypeHandler<List<String>> {
    
    private static final String DELIMITER = ",";
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(DELIMITER, parameter));
    }
    
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : Arrays.asList(value.split(DELIMITER));
    }
    
    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : Arrays.asList(value.split(DELIMITER));
    }
    
    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : Arrays.asList(value.split(DELIMITER));
    }
}