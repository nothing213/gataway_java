package com.service.Impl;

import com.bean.ApiData;
import com.bean.OpenApiData;
import com.dao.ApiDao;
import com.service.ApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    @Resource
    public ApiDao apiDao;

    public boolean insertApi(OpenApiData openApiData) {
        return apiDao.insertApi(openApiData);
    }

//    public List<ApiData> findApi() {
//        return apiDao.findApi();
//    }

    public OpenApiData findApiByName(String apiname){return apiDao.findApiByName(apiname);}
    public List<OpenApiData> findAllApi(){
        return  apiDao.findAllApi();
    }
}
