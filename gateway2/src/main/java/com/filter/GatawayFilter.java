package com.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Date;

@Component
@Slf4j
public class GatawayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***********come in MyLogGateWayFilter:  " + new Date());


        ServerHttpRequest request = exchange.getRequest();
        String uname = request.getQueryParams().getFirst("uname");
//        List<String> uname2 = request.getQueryParams().get("uname");
//        System.out.println(uname2);

        InetSocketAddress remoteAddress = request.getRemoteAddress();
//        log.info("ip名字:"+remoteAddress.getHostName());//获取ip名字
//        System.out.println("ip地址:"+remoteAddress);
//        if(!(remoteAddress.getHostName().equals("activate.navicat.com")))
//        {
//            log.info("非法ip:"+ remoteAddress+" 禁止访问  *____*");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return exchange.getResponse().setComplete();
//        }
        if (uname == null) {
            log.info("用户不能为空");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return chain.filter(exchange);
          //  return exchange.getResponse().setComplete();
        }
        if (uname.equals("wl")) {
            log.info("白名用户" + uname);
            return chain.filter(exchange);
        } else {
            log.info("用户名为" + uname + "非法用户，o(╥﹏╥)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);

            return exchange.getResponse().setComplete();
        }


    }

    @Override
    public int getOrder() {
        return 0;
    }
}
