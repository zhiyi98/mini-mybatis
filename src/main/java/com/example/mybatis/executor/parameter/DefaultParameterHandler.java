package com.example.mybatis.executor.parameter;

import java.sql.PreparedStatement;

public class DefaultParameterHandler implements ParameterHandler {
    private final Object parameter;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) {
        try {
            if (parameter != null) {
                if (parameter.getClass().isArray()) {
                    Object[] params = (Object[]) parameter;
                    for (int i = 0; i < params.length; i++) {
                        Object param = params[i];
                        preparedStatement.setObject(i + 1, param);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
