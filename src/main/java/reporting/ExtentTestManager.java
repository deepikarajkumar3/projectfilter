package reporting;

import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static Map<Long, ExtentTest> extentTestMap = new HashMap<>();

    public static synchronized ExtentTest getExtentTest() {
        return extentTestMap.get((Thread.currentThread().getId()));
    }

    public static synchronized void setExtentTest(ExtentTest test) {
        extentTestMap.put((Thread.currentThread().getId()), test);
    }


}
