package com.wl.controller;

import com.ApiManager;
import com.service.ApiService;
import com.wl.bean.ServerBean;
import com.wl.bean.TokenBucket;
import com.wl.service.Impl.ServerServiceImpl;
import com.wl.service.ServerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Token {
    @Resource
    ServerService serverService ;


    // 创建一个令牌桶，最大容量为100，每毫秒补充1个令牌
    @GetMapping("/getToken")
       public void tokenMethod()
       {
           ServerBean serverLBIndex = serverService.getServerLBIndex();
           Float min = serverLBIndex.getServerLB();
           System.out.println("当前最小lb："+serverLBIndex.getServerLB());
           int flag = 1;
           if(min > 0.8)
           {
               flag = 1;
           }else if(min < 0.4){
               flag = 3;
           }else{
               flag = 2;
           }
           System.out.println(flag);
           TokenBucket tokenBucket = new TokenBucket(100, flag);        // 模拟请求处理
           for (int i = 0; i < 10; i++) {
               int finalFlag = flag;
               new Thread(() -> {
                   while (true) {
                       if (tokenBucket.tryConsume(50)) {
                           System.out.println(Thread.currentThread().getName() + " 执行请求");

                       }
                       else {
                           System.out.println(Thread.currentThread().getName() + " 请求被限流");                    }
                       try {                    // 每个线程等待100毫秒后尝试再次请求
                           Thread.sleep(100);                    }
                       catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }, "线程" + i).start();
           }
       }



}
