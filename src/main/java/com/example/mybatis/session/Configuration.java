package com.example.mybatis.session;

import com.example.mybatis.binding.MapperRegistry;
import com.example.mybatis.mapping.MappedStatement;
import com.example.mybatis.session.defaults.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {

    public static Properties properties = new Properties();
    private final MapperRegistry mapperRegistry = new MapperRegistry();
    private final Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public static String getProperty(String key) {
        return properties.getProperty(key, "");
    }


    public void addMappedStatement(String sqlId, MappedStatement statement) {
        this.mappedStatementMap.put(sqlId, statement);
    }

    public <T> void addMapper(Class<T> type) {
        this.mapperRegistry.addMapper(type);
    }


    public <T> T getMapper(Class<T> type, SqlSession defaultSqlSession) {
        return this.mapperRegistry.getMapper(type, defaultSqlSession);
    }

    public MappedStatement getMappedStatement(String sqlId) {
        return this.mappedStatementMap.get(sqlId);
    }
}
