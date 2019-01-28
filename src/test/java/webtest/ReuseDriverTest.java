package webtest;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.HttpCommandExecutor;

public class ReuseDriverTest {

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Hello World!");

        // 初始化一个chrome浏览器实例，实例名称叫driver
        ReuseWebDriver driver = new ReuseWebDriver("http://localhost:48057", "212f846a-4c9f-495b-97ac-257277e945b0");
        // 最大化窗口
        driver.manage().window().maximize();
        // 设置隐性等待时间
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        // get()打开一个站点
        driver.get("https://www.bing.com");
        // getTitle()获取当前页面title的值
        System.out.println("当前打开页面的标题是： " + driver.getTitle());

        System.out.println(driver.getSessionId());
        System.out.println(driver.getCapabilities());
        System.out.println(((HttpCommandExecutor) driver.getCommandExecutor()).getAddressOfRemoteServer());

        driver.executeScript("alert(\"hello,this is an alert!\")");

        // 关闭并退出浏览器
        driver.quit();
    }
}