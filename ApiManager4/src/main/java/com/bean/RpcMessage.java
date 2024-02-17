package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcMessage {
    String url;
    String method;
//    Object[] args;
    String args;
//    @Override
//    public String toString() {
//        return "RpcMessage{" +
//                "url='" + url + '\'' +
//                ", method='" + method + '\'' +
//                ", args=" + Arrays.toString(args) +
//                '}';
//    }


    @Override
    public String toString() {
        return "RpcMessage{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", args='" + args + '\'' +
                '}';
    }
}
