package com.service.Impl;

import com.bean.ApiGroup;
import com.bean.OpenApiData;
import com.bean.OpenApiGroup;
import com.bean.User;
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

    public boolean saveApi(OpenApiData openApiData) {
        return apiDao.saveApi(openApiData);
    }

    public List<ApiGroup> findAllGroup() {
        return apiDao.findAllGroup();
    }

    public OpenApiGroup findApiByName(String apiName) {
        return apiDao.findApiByName(apiName);
    }

    public OpenApiData findApiById(int id) {
        return apiDao.findApiById(id);
    }

    public List<OpenApiGroup> findAllApi() {
        return apiDao.findAllApi();
    }

    public boolean addApiGroup(ApiGroup apiGroup) {
        return apiDao.addApiGroup(apiGroup);
    }

    ;

    public ApiGroup findGroupByName(String apiGroupName) {
        return apiDao.findGroupByName(apiGroupName);
    }

    public List<OpenApiData> findApiByGroup(String apiGroupName) {
        return apiDao.findApiByGroup(apiGroupName);
    }

    public boolean deleteApiGroup(String apiGroup) {
        return apiDao.deleteApiGroup(apiGroup);
    }

    public boolean deleteApiByGroup(String apiGroup) {
        return apiDao.deleteApiByGroup(apiGroup);
    }

    public boolean deleteApi(int id) {
        return apiDao.deleteApi(id);
    }

    public List<User> findAllUser() {
        return apiDao.findAllUser();
    }
}
