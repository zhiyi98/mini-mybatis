package com.example.mybatis.executor;

import com.example.mybatis.constants.Constant;
import com.example.mybatis.executor.parameter.DefaultParameterHandler;
import com.example.mybatis.executor.parameter.ParameterHandler;
import com.example.mybatis.executor.resultset.DefaultResultSetHandler;
import com.example.mybatis.executor.resultset.ResultSetHandler;
import com.example.mybatis.executor.statement.SimpleStatementHandler;
import com.example.mybatis.executor.statement.StatementHandler;
import com.example.mybatis.mapping.MappedStatement;
import com.example.mybatis.session.Configuration;

import java.sql.*;
import java.util.List;

public class SimpleExecutor implements Executor {

    private final Configuration configuration;

    private static Connection connection;

    static {
        initConnect();
    }

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    private static void initConnect() {
        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        String url = Configuration.getProperty(Constant.DB_URL_CONF);
        String user = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        String password = Configuration.getProperty(Constant.db_PASSWORD);
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        try {

            Connection connection = getConnection();

            MappedStatement mappedStatement = configuration.getMappedStatement(ms.getSqlId());

            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
            parameterHandler.setParameters(preparedStatement);

            ResultSet resultSet = statementHandler.query(preparedStatement);

            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(mappedStatement);

            return resultSetHandler.handleResultSets(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new RuntimeException("Connection is not found! Please checked your configuration!");
        }
        return connection;
    }


}
