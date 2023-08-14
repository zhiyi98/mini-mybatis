package com.example.mybatis.executor.statement;

import com.example.mybatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleStatementHandler implements StatementHandler {

    /** #{}正则匹配 */
    private static final Pattern PARAM_PATTERN = Pattern.compile("#\\{([^{}]*)}");

    private final MappedStatement mappedStatement;


    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public PreparedStatement prepare(Connection connection) throws SQLException {
        String originalSql = mappedStatement.getSql();
        if (originalSql != null && originalSql.length() != 0) {
            return connection.prepareStatement(parseSymbol(originalSql));
        } else {
            throw new SQLException("origin sql is null");
        }

    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    private String parseSymbol(String originalSql) {
        originalSql = originalSql.trim();
        Matcher matcher = PARAM_PATTERN.matcher(originalSql);
        return matcher.replaceAll("?");
    }
}
