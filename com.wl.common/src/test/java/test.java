
//import java.net.URL;//URL的类库

import java.net.*;
import java.io.*;
import java.util.*;

//import java.util.Date;//时间转换
public class test {

    /**
     * @param args
     */
	/* 检测服务器有哪些服务
	public static void testProcotol(String[] args) {
				String host = "www.java2s.com";
				String file = "/index.html";
				String[] schemes = {"http", "https", "ftp", "mailto", "telnet", "file", "ldap", "gopher",
				"jdbc", "rmi", "jndi", "jar", "doc", "netdoc", "nfs", "verbatim", "finger", "daytime",
				"systemresource"};
				for (int i = 0; i < schemes.length; i++) {
				try {
				URL u = new URL(schemes[i], host, file);
				System.out.println(schemes[i] + " is supported\r\n");
				} catch (Exception ex) {
				System.out.println(schemes[i] + " is not supported\r\n");
				}
				}
		}
		*/
    public static void main(String[] args) {
        System.out.println("客户端启动");
        try {
            System.out.print("请输入文件的URL地址: ");//读取用户输入的URL
            Scanner scanner = new Scanner(System.in);
            String addr = scanner.nextLine();
            URL testUrl = new URL("http://" + addr);
            URLConnection urlCon = testUrl.openConnection();
            urlCon.setConnectTimeout(5000);//设定超时时限
            urlCon.connect();
            System.out.println("正链接到" + addr);
            System.out.println("----------------------");
            System.out.println("协议：" + testUrl.getProtocol());//获得协议信息
            System.out.println("端口：" + testUrl.getPort());  //获得端口信息
            System.out.println("主机：" + testUrl.getHost());  //获得主机信息
            System.out.println("文件：" + testUrl.getFile());  //获得文件信息
            System.out.println("内容长度：" + urlCon.getContentLength());
            System.out.println("内容类型：" + urlCon.getContentType());
            System.out.println("内容编码：" + urlCon.getContentEncoding());
            System.out.println("终止日期：" + new Date(urlCon.getExpiration()));
            System.out.println("创建日期：" + new Date(urlCon.getDate()));
            System.out.println("最后修改日期：" + new Date(urlCon.getLastModified()));
            System.out.println("用户交互：" + urlCon.getAllowUserInteraction());
            //System.out.println("超时："+urlCon.getReadTimeout());
            System.out.println("超时时限：" + urlCon.getConnectTimeout());
            if (urlCon instanceof HttpURLConnection) {
                HttpURLConnection hURLCon = (HttpURLConnection) urlCon;
                System.out.println("重定向:" + HttpURLConnection.getFollowRedirects());
                System.out.println("状态码:" + hURLCon.getResponseCode());
                System.out.println("返回的文本信息:" + hURLCon.getResponseMessage());
                System.out.println("请求方法:" + hURLCon.getRequestMethod());
                System.out.println("___________");
                BufferedReader in = new BufferedReader(new InputStreamReader(hURLCon.getInputStream(), "GBK"));
                String line;
                int n = 1;
                while ((line = in.readLine()) != null && n <= 20) {
                    System.out.println(line);
                    n++;
                }
                if (line != null) {
                    System.out.println(". . . . . .");
                }
            }

        } catch (MalformedURLException e) {
            //System.out.println("1");
            //e.printStackTrace();
            //System.out.println(e);
        } catch (IOException e) {
            //System.out.println("2");
            //e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("客户端关闭");
    }
//http://www.oschina.net/uploads/doc/javase-6-doc-api-zh_CN/java/net/URLConnection.html
}
