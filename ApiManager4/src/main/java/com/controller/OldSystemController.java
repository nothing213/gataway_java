package com.controller;

import com.bean.CommonResult;
import com.bean.MessageBody;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模拟访问测试代码
 */

@RestController
public class OldSystemController {

    public void driver_open() {
        System.setProperty("webdriver.chrome.driver", "E:\\homework\\104\\chromedriver.exe");// chromedriver服务地址
    }
//E:\homework\chromedriver
    @GetMapping("/OldSystem")
    public CommonResult<MessageBody> OldSystemNoApi() throws FailingHttpStatusCodeException, InterruptedException {
        driver_open();
        WebDriver driver = new ChromeDriver();
//                driver.get("http://jsj.gzhu.edu.cn/szdw1/jsjkxywlgcxysz.htm");
        driver.get("http://jsj.gzhu.edu.cn/");
        String temple = "师资队伍";
        Thread.sleep(1500);
        WebElement allTeacher2 = driver.findElement(By.linkText(temple));
        WebElement text = driver.findElement(By.linkText(temple));


        text.click();
        System.out.println(driver.getCurrentUrl());
        driver.get(driver.getCurrentUrl());
        WebElement text2 = driver.findElement(By.linkText("计算机科学与网络工程学院师资"));
        text2.click();
        System.out.println(driver.getCurrentUrl());
        List<WebElement> allTeacher = driver.findElements(By.className("mclb"));

        System.out.println(allTeacher.size());
        String ant[] = new String[allTeacher.size()];
        int i = 0;
        // 循环打印搜索结果的标题
        for (WebElement result : allTeacher) {
            ant[i] = result.getText();
            i++;
//            System.out.println(result.getText());
        }

        WebElement teacher = driver.findElement(By.linkText("王员根"));
        teacher.click();
        System.out.println("location:" + teacher.getLocation());
        System.out.println(driver.getTitle());
        driver.quit();
        return new CommonResult(200, "运行成功", ant);

    }

    @PostMapping("/mailLogin")
    public CommonResult<MessageBody> mailLogin(@RequestBody MessageBody messageBody) {
        driver_open();
        WebDriver driver = new ChromeDriver();
//        driver.get("https://passport.baidu.com/v2/?login");
        driver.get(messageBody.getUrl());
        String[] result = messageBody.getMessage().split("-");
        String username = result[0];
        String password = result[1];
        WebElement login_button = driver.findElement(By.id("TANGRAM__PSP_3__footerULoginBtn"));
        login_button.click();

        driver.findElement(By.id("TANGRAM__PSP_3__userName")).clear();
        driver.findElement(By.id("TANGRAM__PSP_3__userName")).sendKeys(username);//"18271197800"
        driver.findElement(By.id("TANGRAM__PSP_3__password")).clear();
        driver.findElement(By.id("TANGRAM__PSP_3__password")).sendKeys(password);//"456851abcd"
        driver.findElement(By.id("TANGRAM__PSP_3__submit")).click();
        driver.quit();
        return new CommonResult(200, "运行成功", "登陆成功");
    }

    @PostMapping("/get_message")
    public CommonResult<MessageBody> get_message(@RequestBody MessageBody messageBody) throws InterruptedException {
        driver_open();
        WebDriver driver = new ChromeDriver();
        //"https://www.baidu.com/"
        System.out.println(messageBody.getUrl());
        driver.get(messageBody.getUrl());

        WebElement search_text = driver.findElement(By.id("kw"));
        search_text.sendKeys(messageBody.getMessage());
        search_text.submit();
        Thread.sleep(2000);

        //匹配第一页搜索结果的标题， 循环打印
        List<WebElement> search_result = driver.findElements(By.xpath("//div/div/h3"));
        String ant[] = new String[search_result.size()];

        //打印元素的个数
        System.out.println(search_result.size());
        int i = 0;
        // 循环打印搜索结果的标题
        for (WebElement result : search_result) {
            ant[i] = result.getText();
            i++;
//            System.out.println(result.getText());
        }
        for (int j = 0; j < search_result.size(); j++) {
            System.out.println(ant[j]);
        }
        System.out.println("-------我是分割线---------");

        //打印第n结果的标题
//        WebElement text = search_result.get(search_result.size() - 10);
//        System.out.println(text.getText());

//        driver.quit();
        return new CommonResult(200, "运行成功", ant);
    }

    @PostMapping("/get_usual")
    public CommonResult get_usual(@RequestBody MessageBody messageBody) throws InterruptedException {
        System.out.println("this is get_usual");
        System.out.println(messageBody);
        driver_open();
//        ChromeOptions options = new ChromeOptions();
//        options.setBinary("Chrome的启动文件路径");
        WebDriver driver = new ChromeDriver();
        //"https://www.baidu.com/"
        System.out.println(messageBody.getUrl());
        driver.get(messageBody.getUrl());

        WebElement search_text = driver.findElement(By.id(messageBody.getElement()));
        search_text.sendKeys(messageBody.getMessage());
        search_text.submit();
        Thread.sleep(2000);

        char temple = messageBody.getResult().charAt(0);
        List<WebElement> search_result;
        if (Character.isLetter(temple)) {
            search_result = driver.findElements(By.className(messageBody.getResult()));
        } else {
            search_result = driver.findElements(By.xpath(messageBody.getResult()));
        }


        String ant[] = new String[search_result.size()];

        //打印元素的个数
        System.out.println(search_result.size());
        int i = 0;
        // 循环打印搜索结果的标题
        for (WebElement result : search_result) {
            ant[i] = result.getText();
            i++;
//            System.out.println(result.getText());
        }
        for (int j = 0; j < search_result.size(); j++) {
            System.out.println(ant[j]);
        }
        System.out.println("-------我是分割线---------");

        //打印第n结果的标题
//        WebElement text = search_result.get(search_result.size() - 10);
//        System.out.println(text.getText());

        driver.quit();
        return new CommonResult(200, "运行成功", ant);
    }


}
