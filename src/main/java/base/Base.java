package base;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reporting.ExtentTestManager;

public class Base {
    protected void logInfo(String sInfo) {
        ExtentTest extentTest = ExtentTestManager.getExtentTest();
        extentTest.log(Status.INFO, sInfo);
    }
}
