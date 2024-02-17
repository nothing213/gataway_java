package com.controller;


import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.bean.OpenApiData;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ApiPost {

    @GetMapping("/interfaceUtil/{data}/{path}")
    public static Object interfaceUtil(@PathVariable String path, @PathVariable String data) {
        Object result = null;
        try {

            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("POST");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
//            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据

            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
//                System.out.println(str);
                result = str;

            }

            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    static public Object runBody(String url, Object body) {
//         url="http://localhost:8001/test_body";

//       String a = "{\"id\":\"122\",\"apiGroup2\":\"testGroup\"}";


        HttpPost httpost = new HttpPost(url); // 设置响应头信息
        httpost.addHeader("Content-Type", "application/json; charset=UTF-8");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000)
                .setConnectTimeout(3000).build();
        httpost.setConfig(requestConfig);
        httpost.setEntity(new StringEntity((String) body, "UTF-8"));
        String returnStr = null;
        try {
            CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpost);
            returnStr = EntityUtils.toString(response.getEntity(), "utf-8");
            return returnStr;

        } catch (Exception e) {
            return null;
        } finally {
            httpost.releaseConnection();

        }
    }

    public static void main(String[] args) {
        String url = "http://localhost:8001/test_body";
        String body = "{\"id\":\"122\",\"apiGroup\":\"testGroup\"}";
//"{'id':'111','apiGroup':'test'}"
        System.out.println(runBody(url, body));
        ;

    }
}
