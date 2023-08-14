package com.example.mybatis.executor;

import com.example.mybatis.mapping.MappedStatement;

import java.util.List;

public interface Executor {
    <E> List<E> doQuery(MappedStatement mappedStatement, Object parameter);
}
