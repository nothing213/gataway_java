package com.service;


import com.bean.ApiGroup;
import com.bean.OpenApiData;
import com.bean.OpenApiGroup;
import com.bean.User;

import java.util.List;

public interface ApiService {

    public boolean insertApi(OpenApiData openApiData);

    public boolean saveApi(OpenApiData openApiData);

    //    public List<ApiData> findApi();
    public OpenApiGroup findApiByName(String apiName);

    public OpenApiData findApiById(int id);

    public List<OpenApiGroup> findAllApi();

    public List<ApiGroup> findAllGroup();

    public boolean addApiGroup(ApiGroup apiGroup);

    public ApiGroup findGroupByName(String apiGroupName);

    public List<OpenApiData> findApiByGroup(String apiGroupName);

    public boolean deleteApiGroup(String apiGroup);

    public boolean deleteApiByGroup(String apiGroup);

    public boolean deleteApi(int id);

    public List<User> findAllUser();
}
