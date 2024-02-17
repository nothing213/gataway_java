package com.controller;



import com.bean.CommonResult;
import com.bean.RpcMessage;
import org.apache.axis.utils.StringUtils;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
/**
 * 协议适配
 */
@RestController
class CalculateClient {


    @GetMapping("/SoapMethod2")
    public CommonResult<RpcMessage> demo()
    {
        String result = null;
        try {
            String endpoint = "https://amr.sz.gov.cn/szjxjgw/services/Spemainser?wsdl";
            //直接引用远程的wsdl文件
            //以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            /**
             *注意这里，要设置Namespace
             * new QName("http://webDemo.example.com.java.main/",
             */

            call.setOperationName(new QName("","replaceKH"));//WSDL里面描述的接口名称
            call.addParameter("replaceKH", XMLType.XSD_STRING,
                    ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
//     ntorgcode':'444','useorgmanager':'444','useorgpone':'444','elevlongit':'444','elevlatit':'444'},'halfmonthmaintitem':{'hm1':'2','hm2':'2','hm3':'2','hm4':'2','hm5':'2','hm6':'2','hm7':'2','hm8':'2','hm9':'2','hm10':'2','hm11':'2','hm12':'2','hm13':'2','hm14':'2','hm15':'2','hm16':'2','hm17':'2'},'quartermaintitem':{'qtr1':'3','qtr2':'3','qtr3':'3','qtr4':'3','qtr5':'3','qtr6':'3','qtr7':'3','qtr8':'3','qtr9':'3','qtr10':'3'},'halfyearmaintitem':{'hy1':'4','hy2':'4','hy3':'4','hy4':'4','hy5':'4','hy6':'4','hy7':'4','hy8':'4','hy9':'4','hy10':'4'},'yearmaintitem':{'y1':'5','y2':'5','y3':'5','y4':'5','y5':'5','y6':'5','y7':'5','y8':'5','y9':'5','y10':'5','y11':'5','y12':'5','y13':'5','y14':'5'}}}"};
            result = (String)call.invoke(new Object[]{"{'id':'1','name':'Chaina'}"});

            System.out.println("result is :"+result);
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
        return new CommonResult(200,"执行成功",result);
    }

    //实现WebService上发布的服务调用
    @PostMapping("/SoapMethod")
    public CommonResult<RpcMessage> CallMethod(@RequestBody RpcMessage rpcMessage ) {

        String result = null;
//        System.out.println(rpcMessage);
//        System.out.println("url:"+rpcMessage.getUrl());
//        System.out.println("methods:"+rpcMessage.getMethod());
//        System.out.println("args:"+rpcMessage.getArgs());
        if(StringUtils.isEmpty(rpcMessage.getUrl())) {
            return new CommonResult(404,"url地址为空",null);
        }
        if(rpcMessage.getMethod().equals("")) {
            return new CommonResult(404,"method is empty",null);
        }
        if(StringUtils.isEmpty((String) rpcMessage.getArgs())){
            return new CommonResult(404,"parm is empty",null);
        }
        Call rpcCall = null;

        try {
            //实例websevice调用实例
            Service webService = new Service();
            rpcCall = (Call) webService.createCall();
            rpcCall.setTargetEndpointAddress(new java.net.URL(rpcMessage.getUrl()));
            rpcCall.setOperationName(rpcMessage.getMethod());

            //执行webservice方法

            String rslt =  (String)rpcCall.invoke(new Object[]{rpcMessage.getArgs()});
            result = String.valueOf(rslt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResult(200,"执行成功",result);

    }

}

