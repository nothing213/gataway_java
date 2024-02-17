package com.wl.dao;

import com.wl.bean.ServerBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServerDao {
    public List<ServerBean> getServerMsg();
    public boolean setServerMsg(ServerBean serverBean);
}
