package com.controller;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟访问测试代码
 */
@RestController
public class MailLoginController {

        @GetMapping("/badminton")
        public void badminton() throws Exception
        {
            System.setProperty("webdriver.chrome.driver", "E:\\code\\110.0.5481.77\\chromedriver.exe");// chromedriver服务地址
            WebDriver driver = new ChromeDriver();
            driver.get("https://usc.gzhu.edu.cn/infoplus/form/2330756/render?back=1");

            WebElement user = driver.findElement(By.id("un"));
            user.sendKeys("2112006204");
//            user.click();

            WebElement pass = driver.findElement(By.id("pd"));
            pass.sendKeys("wlmp01785@163.com");
//            pass.click();

            driver.findElement(By.id("index_login_btn")).click();//登陆

            Thread.sleep(1000);
            driver.findElement(By.id("V1_CTRL22")).sendKeys("18271197800");//填写手机号

            WebElement badmintonball =driver.findElement(By.id("id"));//选择羽毛球
            badmintonball.sendKeys("羽毛球");
            badmintonball.click();

           WebElement location =  driver.findElement(By.id("V1_CTRL25402_activeInput"));//选择风雨
            location.sendKeys("风雨跑廊羽毛球场");
            location.click();

            driver.findElement(By.className("suggest_unselected")).click();//第二天

            driver.findElement(By.id("V1_CTRL17_4")).click();//选择4号场

            driver.findElement(By.id("infoplus_action_3610_1")).click();//提交

        }



}
