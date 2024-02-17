package com.wl.bean;

import lombok.Data;
import org.apache.catalina.Server;

@Data
public class ServerBean {
    private int ServerID;
    private String ServerName;
    private String ServerHost;
    private String ServerPort;
    private Float ServerLB;

    public ServerBean()
    {

    }
    public ServerBean(String Name,String Host,String Port,Float LB)
    {
        ServerName = Name;
        ServerHost = Host;
        ServerPort = Port;
        ServerLB = LB;
    }
    @Override
    public String toString() {
        return "ServerBean{" +
                "ServerID=" + ServerID +
                ", ServerName='" + ServerName + '\'' +
                ", ServerHost='" + ServerHost + '\'' +
                ", ServerPort='" + ServerPort + '\'' +
                ", ServerLB=" + ServerLB +
                '}';
    }
}
