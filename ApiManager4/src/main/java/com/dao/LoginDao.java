package com.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {
    public Boolean findUser(String username, String password);
}
