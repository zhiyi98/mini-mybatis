package com.example.mybatis;

import com.example.mybatis.entity.User;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.session.SessionFactory;
import com.example.mybatis.session.SqlSession;
import com.example.mybatis.session.SqlSessionFactoryBuilder;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SessionFactory factory = new SqlSessionFactoryBuilder().build("application.properties");
        SqlSession session = factory.openSession();

        UserMapper mapper = session.getMapper(UserMapper.class);

        List<User> all = mapper.getAll();
        System.out.println("all = " + all);

        User user = mapper.getUser(1L);
        System.out.println("user = " + user);

    }

}
