package com.dao;

import com.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisterDao {
    public int registerUser(@Param("user") User user);
}
