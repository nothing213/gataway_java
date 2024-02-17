package com.dao;


import com.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ApiDao {
    //@Param("openApiData")
    public boolean insertApi(OpenApiData openApiData);

    public boolean saveApi(OpenApiData openApiData);

    public OpenApiGroup findApiByName(String apiName);//根据名字查找api

    public OpenApiData findApiById(int id);//根据名字查找api

    public List<OpenApiGroup> findAllApi();//查找所有api

    public List<ApiGroup> findAllGroup();//查找所有组

    public boolean addApiGroup(ApiGroup apiGroup);

    public ApiGroup findGroupByName(String apiGroup);//通过名字查找分组名

    public List<OpenApiData> findApiByGroup(String apiGroup);//通过组名查找同一组api

    public boolean deleteApiGroup(String apiGroup);//直接删除空的分组

    public boolean deleteApiByGroup(String apiGroup);//如果该分组有api则把该分组下的api一并删除

    public boolean deleteApi(int id);//根据id删除api

    public List<User> findAllUser();//查找所有用户
}
