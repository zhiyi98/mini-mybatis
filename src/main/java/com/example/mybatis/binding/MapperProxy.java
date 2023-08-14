package com.example.mybatis.binding;

import com.example.mybatis.mapping.MappedStatement;
import com.example.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        return this.execute(method, args);
    }

    private Object execute(Method method, Object[] args) {
        String sqlId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(sqlId);

        Object result = null;
        switch (ms.getSqlCommandType()) {
            case SELECT:
                Class<?> returnType = method.getReturnType();
                if (Collection.class.isAssignableFrom(returnType)) {
                    result = sqlSession.selectList(sqlId, args);
                } else {
                    result = sqlSession.selectOne(sqlId, args);
                }
                break;
            case INSERT:
            case DELETE:
            case UPDATE:
            default:
                break;
        }

        return result;
    }
}
