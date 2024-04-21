package biz.bna.project.utils;

import biz.bna.project.rowmapper.RowMapForObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DatabaseUtils {
    final String databaseUrl = "jdbc:postgresql://localhost:5432/DEVELOP?currentSchema=dbo";
    final String dataBaseUsername = "SYSDBA";
    final String databaseUserPassword = "masterkey";

    public DatabaseUtils() {
    }

    public void executeSql(String sql){
        try(Connection connection = DriverManager.getConnection(databaseUrl, dataBaseUsername, databaseUserPassword)){
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void executeSql(String sql, Object param0Value){
        executeSql(sql, Map.of(1, param0Value));
    }

    public void executeSql(String sql, Map<Integer, Object> params){
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            setParams(statement, params);
            statement.execute();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> List<T> select(RowMapForObject<T> rowMapper, String sql, Map<Integer, Object> params){
        try (Connection connection = getConnection()){
            List<T> result = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(sql);
            setParams(statement, params);
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while(resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet, i));
                i++;
            }
            return result;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Integer getSequenceNextValue(String sequenceName){
        try (Connection connection = getConnection()){
            String sql = String.format("SELECT nextval('%s')", sequenceName);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }else {
                return 1;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> List<T> select(RowMapForObject<T> rowMapper, String sql){
        return select(rowMapper, sql, Map.of());
    }

    public <T> List<T> select(RowMapForObject<T> rowMapper, String sql, Object param0Value){
        return select(rowMapper, sql, Map.of(1, param0Value));
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, dataBaseUsername, databaseUserPassword);
    }

    private void setParams(PreparedStatement statement, Map<Integer, Object> params){
        params.keySet().stream()
                .forEach(key -> {
                    try {
                        Object obj = params.get(key);
                        if (obj instanceof Integer) {
                            statement.setInt(key, (Integer) obj);
                        }
                        if (obj instanceof String) {
                            statement.setString(key, (String) obj);
                        }
                        if (obj instanceof Date) {
                            statement.setObject(key, obj);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
