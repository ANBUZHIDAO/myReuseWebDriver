package webtest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.HttpCommandExecutor;

public class ChromeDriverTest {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");

        ChromeDriverService chromeDrService = ChromeDriverService.createDefaultService();

        // 初始化一个chrome浏览器实例
        ChromeDriver driver = new ChromeDriver(chromeDrService);
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
