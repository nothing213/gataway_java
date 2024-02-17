package com.bean;

import lombok.Data;

@Data
public class ServerBean {
    private int ServerID;
    private String ServerName;
    private String ServerHost;
    private String ServerPort;
    private Float ServerLB;

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
