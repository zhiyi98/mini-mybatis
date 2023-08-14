package com.example.mybatis.mapping;

import com.example.mybatis.constants.SqlType;
import lombok.Data;

@Data
public class MappedStatement {

    private String namespace;

    private String sqlId;

    private String sql;

    private String resultType;

    private SqlType sqlCommandType;

}
