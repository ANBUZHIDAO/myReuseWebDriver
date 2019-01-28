# ReuseWebDriver
Selenium Webdriver重新使用已打开的浏览器实例 更新于2019-1-28
在win10系统简单测试过Google Chrome 71， Firefox 64，IE11

# Selenium Webdriver 工作原理简介
    1）Selenium代码调用API实际上根据 The WebDriver Wire Protocol 发送不同的HttpRequest 到 WebdriverServer。
    IEDriver下载地址：http://selenium-release.storage.googleapis.com/index.html
    ChromerDriver下载地址： http://npm.taobao.org/mirrors/chromedriver/ 
    Firefox之前老版本是以插件的形式，直接在selenium-server-standalone-XXX.jar里了： 
    webdriver.xpi （selenium-server-standalone-2.48.2.jar中/org/openqa/selenium/firefox/目录下） 
    Firefox现在抛弃XPI插件框架了，现在最新的如Firefox64需要使用geckodriver。
    GeckoDriver下载地址：https://github.com/mozilla/geckodriver/releases
    
    WebDriver协议是RESTful风格的。W3C Webdriver标准协议内容：http://www.w3.org/TR/webdriver/

    2）WebdriverServer接收到HttpRequest之后，根据不同的命令在操作系统层面去触发浏览器的”native事件“， 
    模拟操作浏览器。WebdriverServer将操作结果Http Response返回给代码调用的客户端。

    可以参考 我之前在CSDN写的使用代理可以看到协议具体内容： 
    http://blog.csdn.net/wwwqjpcom/article/details/51232302

# 如何实现使用已打开的浏览器
    想要实现Webdriver重新使用已打开的浏览器实例。
    根据原理,需要WebDriverServer的地址、一个可用的已有Session。
    
    据此实现了ReuseWebDriver。
    使用中需要将一个已打开的WebDriver实例的WebDriverServer地址，SessionId保存起来，
    然后用于初始化ReuseWebDriver。

# 测试例子

    参考 src/test/java/webtest 目录下测试文件。  
    ChromeDriverTest.java、FirefoxDriverTest.java、IEDriverTest.java分别是3种浏览器启动后不退出，  
    然后使用打印出来的WebDriverServer的地址和SessionId用于ReuseDriverTest.java中即可重新使用已打开的浏览器session。
