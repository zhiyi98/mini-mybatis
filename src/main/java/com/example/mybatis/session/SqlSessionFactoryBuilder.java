package com.example.mybatis.session;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SessionFactory build(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public SessionFactory build(InputStream inputStream) {
        try {
            Configuration.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
    }


}
