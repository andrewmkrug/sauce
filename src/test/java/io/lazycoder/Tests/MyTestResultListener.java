package io.lazycoder.Tests;

import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class MyTestResultListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
//        SauceREST r = new SauceREST("username", "access-key");
//        //String job_id = (((RemoteWebDriver) driver).getSessionId()).toString();
//        if(result.getStatus() == ITestResult.SUCCESS)
//            r.jobPassed(job_id);    }
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        // do what you want to do
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // do what you want to do
    }
}

