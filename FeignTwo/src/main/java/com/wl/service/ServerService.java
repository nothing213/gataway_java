package com.wl.service;

import com.wl.bean.ServerBean;

import java.util.List;

public interface ServerService {
    public ServerBean getServerLBIndex();
    public List<ServerBean> getServerMsg();

    public boolean setServerMsg(ServerBean serverBean);
}
