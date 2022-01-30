package qa.opentext;

import base.TestBase;
import dataprovider.TestDataProvider;
import enums.CountryList;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;



public class TestCountriesName extends TestBase {

    List<String> inputCountryList;
    List<String> filterCountryList = new ArrayList<>();



    @BeforeTest()
    public void convertString() {
        List<CountryList.Countries> items1 = Arrays.asList(CountryList.Countries.values());
        for (CountryList.Countries enumCha : items1) {
            filterCountryList.add(String.valueOf(enumCha));
        }

    }



    @Test(dataProvider = "jsonDataProvider", dataProviderClass = TestDataProvider.class, priority=1)
    public void test1(Map<String, String> testData) {
        inputCountryList = Arrays.asList(testData.get("countrylist").substring(1, testData.get("countrylist").length() - 1).replace("\"", "").split(","));


        ArrayList<String> matchList = new ArrayList<>();
        ArrayList<String> nonMatchList = new ArrayList<>();

        for (int i = 0; i < inputCountryList.size(); i++) {
            boolean flag = false;
            List<String> list1 = new ArrayList<>();
            for (int j = 0; j < filterCountryList.size(); j++) {
                if (inputCountryList.get(i) == null || inputCountryList.get(i).isEmpty() || inputCountryList.get(i).trim().isEmpty()){
                    break;
                }
                if ((filterCountryList.get(j)).contains(inputCountryList.get(i).trim().toUpperCase(Locale.ROOT))) {
                    flag = true;
                    list1.add(filterCountryList.get(j));
                }
            }
            if (flag) {
                matchList.addAll(list1);
            } else {
                nonMatchList.add(inputCountryList.get(i));
            }

        }




        boolean flag;
       if(nonMatchList.size() >=1){
           flag = false;
        }
        else{
            flag = true;
        }

        logInfo(testData.get("description"));
        logInfo("Input Countries: "+inputCountryList);


        assertTrue(flag,
               "Given country list input present in the filter list: "+matchList,
              "In the given country list input onr or more names not present in the filter list: "+nonMatchList);


        Collections.sort(matchList);
        logInfo("Filtered list in alphabetical order: "+matchList);

        Collections.sort(matchList, Collections.reverseOrder());
        logInfo("Filtered list in non alphabetical order: "+matchList);

    }


}



