package com.example.mybatis.session.defaults;

import com.example.mybatis.executor.Executor;
import com.example.mybatis.executor.SimpleExecutor;
import com.example.mybatis.mapping.MappedStatement;
import com.example.mybatis.session.Configuration;
import com.example.mybatis.session.SqlSession;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <E> List<E> selectList(String sqlId, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(sqlId);
        return this.executor.doQuery(mappedStatement, parameter);
    }

    @Override
    public <T> T selectOne(String sqlId, Object parameter) {
        List<T> results = this.selectList(sqlId, parameter);
        return results != null ? results.get(0) : null;
    }

}
