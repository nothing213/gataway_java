package com.dao;

import com.bean.ServerBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServerDao {
    public List<ServerBean> getServer();
}
