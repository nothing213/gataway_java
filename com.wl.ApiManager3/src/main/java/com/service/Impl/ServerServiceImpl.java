package com.service.Impl;

import com.bean.CommonResult;
import com.bean.ServerBean;
import com.dao.ServerDao;
import com.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    @Resource
    ServerDao serverDao;
    @Override
    public List<ServerBean> getServer() {
        return serverDao.getServer();
    }


}
