package com.isjay.selenium

import java.io.File

import org.openqa.selenium.chrome.ChromeDriver


/**
  * Created by huangguowei on 15/6/5.
  */
object Main {

  def main(args: Array[String]) = {
    val startTime = System.currentTimeMillis()

    //    System.setProperty("webdriver.firefox.bin",
    //      System.getProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox"))

    val chromeDriverPath = new File("selenium_chrome_drivers/chromedriver").getAbsolutePath()
    println(chromeDriverPath)

    System.setProperty("webdriver.chrome.driver",
      System.getProperty("webdriver.chrome.driver", chromeDriverPath))
    val driver = new ChromeDriver()

    //驱动的网址
    driver.get("http://www.baidu.com/");

    println("driver.getTitle=" + driver.getTitle)

    //
    //    //浏览器窗口变大
    //    driver.manage().window().maximize();
    //
    //    //定位输入框元素
    //    val txtbox = driver.findElement(By.name("wd"));
    //
    //    //在输入框输入文本
    //    txtbox.sendKeys("HelloWorld");
    //
    //    //定位按钮元素
    //    val btn = driver.findElement(By.id("su"));
    //
    //    //点击按钮
    //    btn.click();


    //关闭驱动
    driver.close();

    val costTime = (System.currentTimeMillis() - startTime) / 1000f
    println("Program cost " + costTime + "s!")
  }

}
