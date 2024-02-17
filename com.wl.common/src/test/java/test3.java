import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class test3 {

    public static void main(String[] args) {
        runBroswer("https://www.bilibili.com/video/BV1SB4y1K77A?p=26", 1);
    }

    public static void runBroswer(String url, int flag) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(url);
                desktop.browse(uri); //使用系统默认的浏览器执行这个url
                Thread.sleep(2000);
                //Runtime.getRuntime().exec("taskkill /F /IM Iexplore.exe");
                Runtime.getRuntime().exec("taskkill  /IM firefox.exe"); //因为我系统默认的是火狐,然后关闭火狐浏览器
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
