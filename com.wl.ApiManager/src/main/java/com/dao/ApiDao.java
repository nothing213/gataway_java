package com.dao;


import com.bean.ApiData;
import com.bean.OpenApiData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ApiDao {
//@Param("openApiData")
    public  boolean insertApi(OpenApiData openApiData);

    public OpenApiData findApiByName(String apiname);
    public List<OpenApiData> findAllApi();
}
