package com.wl.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import com.google.gson.JsonArray;
import com.wl.bean.ServerBean;
import com.wl.dao.ServerDao;
import com.wl.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    @Override
    public boolean setServerMsg(ServerBean serverBean) {
        return  serverDao.setServerMsg(serverBean);
    }

    @Resource
    private ServerDao serverDao;
    @Override
    public List<ServerBean> getServerMsg() {
        return serverDao.getServerMsg();
    }


    @Override
    public ServerBean getServerLBIndex(){

        List<ServerBean> serverBean =  this.getServerMsg();

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
