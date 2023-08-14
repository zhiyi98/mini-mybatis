package com.example.mybatis.mapper;

import com.example.mybatis.entity.User;

import java.util.List;

public interface UserMapper {

    User getUser(Long id);

    List<User> getAll();

}
