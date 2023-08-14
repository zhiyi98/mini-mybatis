package com.example.mybatis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatementHandler {

    PreparedStatement prepare(Connection connection) throws SQLException;

    ResultSet query(PreparedStatement preparedStatement) throws SQLException;

}
