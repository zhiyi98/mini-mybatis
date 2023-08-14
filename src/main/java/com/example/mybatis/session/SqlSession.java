package com.example.mybatis.session;

import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<T> clazz);

    Configuration getConfiguration();

    <E> List<E> selectList(String sqlId, Object parameter);

    <T> T selectOne(String sqlId, Object parameter);

}
