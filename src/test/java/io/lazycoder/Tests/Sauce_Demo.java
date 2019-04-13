package io.lazycoder.Tests;

import com.saucelabs.saucerest.SauceREST;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
//@Listeners(MyTestResultListener.class)
public class Sauce_Demo {
//
    private static String Username = "a-team";
    private static String AccessKey = "f0b86e5b-d9e7-4b05-8a4a-c2a7716a8e97";


//    private static String Username = "andrewsaucetrial1";
//    private static String AccessKey = "483c3c71-27a7-4380-b0c6-b9f0fa4769f2";

    static String Hub = String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub", Username, AccessKey);


    @DataProvider(name = "hardCodedBrowsers", parallel = true )
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"chrome", "64", "Windows 10", 82},
                new Object[]{"safari", "7", "OS X 10.9", 83},
                new Object[]{"chrome", "64", "OS X 10.9", 84},
                new Object[]{"firefox", "35", "OS X 10.9", 85},
                new Object[]{"firefox", "35", "Windows 7", 86},
                new Object[]{"edge","14.14393", "Windows 10", 87},
                new Object[]{"chrome", "64", "Windows 10", 88},
                new Object[]{"firefox", "35", "Windows 10", 89}
       };
    }


    @Test(dataProvider="hardCodedBrowsers")
    public void CreateAccount(String browser, String version, String os, Integer id) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        if(browser=="chrome"){
            caps = DesiredCapabilities.chrome();
        }
        else if(browser=="firefox"){
            caps = DesiredCapabilities.firefox();
        }
        else if(browser=="safari"){
            caps = DesiredCapabilities.safari();
        }
        else if(browser=="edge"){
            caps = DesiredCapabilities.edge();
        }
        caps.setCapability("version", version);
        caps.setCapability("platform", os);

        caps.setCapability("name", "Creating Studio Code Account");
        caps.setCapability("tags", "Creating");
        caps.setCapability("build", "build-1234");

        WebDriver driver = new RemoteWebDriver(new URL(Hub), caps);
        //driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        SauceREST r = new SauceREST(Username, AccessKey);
        String job_id = (((RemoteWebDriver) driver).getSessionId()).toString();
        System.out.println(job_id);
        driver.get("https://studio.code.org/courses");
        driver.findElement(By.cssSelector("a[href=\"/users/sign_up\"]")).click();
        new Select(driver.findElement(By.id("user_user_type"))).selectByValue("student");

        String email = String.format("andrew+%s@lazycoder.io", Integer.toString(id));
        driver.findElement(By.id("user_email")).sendKeys(email);
        driver.findElement(By.id("user_password")).sendKeys("WeakPassword");
        driver.findElement(By.id("user_password_confirmation")).sendKeys("WeakPassword");

        driver.findElement(By.id("user_name")).sendKeys("Andrew");
        new Select(driver.findElement(By.id("user_user_age"))).selectByValue("21+");

        new Select(driver.findElement(By.id("user_gender"))).selectByValue("m");

        driver.findElement(By.id("signup-button")).click();
        //new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("homepage-container")));

        try{
            Assert.assertTrue(driver.findElement(By.id("homepage-container")).getText().contains("My Dashboard"));
            r.jobPassed(job_id);
        }
        catch (Exception e){
            r.jobFailed(job_id);
        }
    }

//    @Test(dataProvider="hardCodedBrowsers")
//    public void login(String browser, String version, String os) throws MalformedURLException {
//        DesiredCapabilities caps = new DesiredCapabilities();
//        if(browser=="chrome"){
//            caps = DesiredCapabilities.chrome();
//        }
//        else if(browser=="firefox"){
//            caps = DesiredCapabilities.firefox();
//        }
//        else if(browser=="safari"){
//            caps = DesiredCapabilities.safari();
//        }
//        else if(browser=="edge"){
//            caps = DesiredCapabilities.edge();
//        }
//        caps.setCapability("version", version);
//        caps.setCapability("platform", os);
//
//        caps.setCapability("name", "Creating Studio Code Account");
//        caps.setCapability("tags", "Creating");
//        caps.setCapability("build", "build-1234");
//
//        WebDriver driver = new RemoteWebDriver(new URL(Hub), caps);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//        SauceREST r = new SauceREST(Username, AccessKey);
//        String job_id = (((RemoteWebDriver) driver).getSessionId()).toString();
//        System.out.println(job_id);
//
//        driver.get("http://the-internet.herokuapp.com/login");
//        driver.findElement(By.id("username")).sendKeys("tomsmith");
//        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
//        driver.findElement(By.cssSelector("button")).click();
//
//        String jobs = r.getJobs();
//        try{
//            Assert.assertTrue("success message not present",
//            driver.findElement(By.cssSelector(".flash.success")).isDisplayed());
//            r.jobPassed(job_id);
//        }
//        catch (Exception e){
//            r.jobFailed(job_id);
//        }
//        driver.quit();
//    }

}
