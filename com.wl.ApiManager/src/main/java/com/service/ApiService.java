package com.service;


import com.bean.ApiData;
import com.bean.OpenApiData;

import java.util.List;

public interface ApiService {

    public boolean insertApi(OpenApiData openApiData);
//    public List<ApiData> findApi();
    public OpenApiData findApiByName(String apiname);
    public List<OpenApiData> findAllApi();
}
