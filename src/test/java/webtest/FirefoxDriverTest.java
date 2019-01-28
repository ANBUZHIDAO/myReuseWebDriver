package webtest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.HttpCommandExecutor;

public class FirefoxDriverTest {
    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe");

        // 初始化一个firefox浏览器实例
        FirefoxDriver driver = new FirefoxDriver();
        // 最大化窗口
        driver.manage().window().maximize();
        // 设置隐性等待时间
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        // get()打开一个站点
        driver.get("https://www.baidu.com");
        // getTitle()获取当前页面title的值
        System.out.println("当前打开页面的标题是： " + driver.getTitle());

        System.out.println(driver.getSessionId());
        System.out.println(((HttpCommandExecutor) driver.getCommandExecutor()).getAddressOfRemoteServer());
        // 关闭并退出浏览器
        // driver.quit();
    }
}
