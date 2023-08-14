package com.example.mybatis.executor.resultset;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler {

    <E> List<E> handleResultSets(ResultSet resultSet);

}
