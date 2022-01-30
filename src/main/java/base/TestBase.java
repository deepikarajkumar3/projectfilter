package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentReportManager;
import reporting.ExtentTestManager;

import java.lang.reflect.Method;

public class TestBase extends Base{

    private static ExtentReports extentReports;
    protected static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeSuite
    @Parameters({"properties"})  //reads parameter value for name="properties" from testng.xml
    public static void beforeSuite(String sCountryTD, ITestContext testContext){

        String sTestCountryTD = System.getProperty("folderTest", sCountryTD);
        testContext.getSuite().setAttribute("folderTest", sTestCountryTD);
        System.out.println("running test for folder : "+sTestCountryTD);

        String suiteName = testContext.getSuite().getName();
        extentReports = ExtentReportManager.createReportInstance(suiteName);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        Test test = method.getDeclaredAnnotation(Test.class);
        ExtentTest extentTest = extentReports.createTest(method.getName(), test.description());
        ExtentTestManager.setExtentTest(extentTest);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult iTestResult) {
        ExtentTest extentTest = ExtentTestManager.getExtentTest();
        if (iTestResult.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, iTestResult.getMethod().getMethodName() + " is a pass");
        } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, iTestResult.getThrowable().getMessage());
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, iTestResult.getThrowable().getMessage());
        }
    }

    @AfterSuite
    public static void afterSuite(ITestContext testContext) {
        extentReports.flush();
    }

    @Override
    protected void logInfo(String sInfo) {
        ExtentTest extentTest = ExtentTestManager.getExtentTest();
        extentTest.log(Status.INFO, sInfo);
    }


    protected void assertTrue(boolean bool,String passMessage, String failMessage) {
        ExtentTest extentTest = ExtentTestManager.getExtentTest();
        if (bool) {
            extentTest.log(Status.PASS, "passed: " + passMessage);
        } else {
            extentTest.log(Status.FAIL, "failed: " + failMessage);
        }
        Assert.assertTrue(bool, failMessage);
    }

}

