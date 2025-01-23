package cn.tsguas.osc.utils;

import cn.tsguas.osc.model.domain.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JsonTypeHandler extends BaseTypeHandler<List<Metric>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Metric> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, objectMapper.writeValueAsString(parameter)); // 将 List<Metric> 转换为 JSON 字符串
        } catch (IOException e) {
            throw new SQLException("Failed to serialize JSON", e);
        }
    }

    @Override
    public List<Metric> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String json = rs.getString(columnName);
            return json == null ? null : objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Metric.class)); // 从 JSON 字符串转换为 List<Metric>
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JSON", e);
        }
    }

    @Override
    public List<Metric> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String json = rs.getString(columnIndex);
            return json == null ? null : objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Metric.class));
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JSON", e);
        }
    }

    @Override
    public List<Metric> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String json = cs.getString(columnIndex);
            return json == null ? null : objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Metric.class));
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JSON", e);
        }
    }
}
