package com.example.mybatis.executor.resultset;

import com.example.mybatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {
    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        List<E> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Class<?> resultTypeClass = Class.forName(mappedStatement.getResultType());
                E entity = (E) resultTypeClass.newInstance();
                Field[] declaredFields = resultTypeClass.getDeclaredFields();

                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    if (String.class.equals(fieldType)) {
                        field.set(entity, resultSet.getString(field.getName()));
                    }

                    else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
                        field.set(entity, resultSet.getInt(field.getName()));
                    }

                    else if (long.class.equals(fieldType) || Long.class.equals(fieldType)) {
                        field.set(entity, resultSet.getLong(field.getName()));
                    }

                    else {
                        field.set(entity, resultSet.getObject(field.getName()));
                    }
                }
                result.add(entity);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
