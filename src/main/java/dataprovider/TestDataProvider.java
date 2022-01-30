package dataprovider;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TestDataProvider {
    @DataProvider(name = "jsonDataProvider")
    public Object[][] testJsonDataProvider(Method callingMethod, ITestContext testContext) {
        Object[][] arrayOfDataSetMap = null;

        String methodName = callingMethod.getName();

        String sInvokingClassPackageName = callingMethod.getDeclaringClass().getPackage().getName();
        String sFolderName = sInvokingClassPackageName.substring(sInvokingClassPackageName.lastIndexOf('.') + 1);
        String fullyQualifiedClassName = callingMethod.getDeclaringClass().getName();

        String[] fullyQualifiedClassNameParts = fullyQualifiedClassName.split("\\.");
        String className = fullyQualifiedClassNameParts[fullyQualifiedClassNameParts.length - 1];
        String relevantTestDataDirectory = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "test" + File.separator +
                "testdatasheet" + File.separator +
                sFolderName;
        System.out.println(relevantTestDataDirectory);

        String dataFilePath = relevantTestDataDirectory + File.separator + className + ".json";
        System.out.println(dataFilePath);

        JSONParser parser = new JSONParser();


        try (FileReader fileReader = new FileReader(dataFilePath)) {

            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray testData = (JSONArray) jsonObject.get(methodName);

            int totalRows = testData.size();

            arrayOfDataSetMap = new Object[totalRows][1];

            Iterator testDataIterator = testData.iterator();
            int rowNum = 0;
            while (testDataIterator.hasNext()) {
                Map<String, String> dataSetMap = new HashMap<>();
                JSONObject testDataRowObject = (JSONObject) testDataIterator.next();
                Iterator<String> testDataColumnTitlesIterator = testDataRowObject.keySet().iterator();
                while (testDataColumnTitlesIterator.hasNext()) {
                    String sKey = testDataColumnTitlesIterator.next();
                    dataSetMap.put(sKey.toLowerCase(), testDataRowObject.get(sKey).toString());
                }
                arrayOfDataSetMap[rowNum][0] = dataSetMap;
                rowNum++;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return arrayOfDataSetMap;
    }


}
