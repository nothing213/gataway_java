package com.controller;

import com.bean.CommonResult;
import com.bean.ServerBean;
import com.service.ServerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.controller.CSVFile.createCSVFile;

@RestController
public class CSVController {

    @Resource
    ServerService serverService;

    @GetMapping("/CSVStart")
    public void init() throws Exception
    {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            CSVStart();
        } ,0, 30, TimeUnit.SECONDS);
    }

    public void CSVStart(){

        List<Object> exportData = new ArrayList<Object>();
        exportData.add("Times");
        exportData.add("loaders");
        List<ServerBean> server = serverService.getServer();

//        for(ServerBean s:server)
//        {
//            System.out.println(s.getServerPort()+":"+s.getServerLB());
//        }
        List<List<Object>> datalist = new ArrayList<List<Object>>();
        int flag = 5;
        for(int i = 0;i<server.size();i++)
        {
            List data = new ArrayList();
            data.add(flag);
            data.add(server.get(i).getServerLB());
            flag = flag+5;
            datalist.add(data);
        }

        String path = "C:\\Users\\pc\\pythonCode\\wll\\file";
        String fileName = "loaders";

        File file = createCSVFile(exportData, datalist, path, fileName);
        String fileName2 = file.getName();
        System.out.println("文件名称：" + fileName2);
//        return new CommonResult(200,"success","文件名称：" + fileName2);
    }
}
