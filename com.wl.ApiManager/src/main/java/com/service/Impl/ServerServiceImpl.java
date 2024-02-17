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
    @Override
    public ServerBean getServerLBIndex(){

        List<ServerBean> serverBean =  this.getServer();

        Float[] floats = new Float[serverBean.size()];

        for(int i = 0;i<serverBean.size();i++)
        {
            floats[i] = serverBean.get(i).getServerLB();
        }
        int flag = 0;
        Float min = floats[0];
        for(int i = 0;i< floats.length;i++)
        {
            if(floats[i] < min)
            {
                min = floats[i];
                flag = i;
            }
        }

        System.out.println("min lb is "+ min);

        return serverBean.get(flag);
    }
}
