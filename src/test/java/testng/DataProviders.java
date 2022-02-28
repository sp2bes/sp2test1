package testng;

import org.testng.annotations.DataProvider;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProviders {
    @DataProvider(name = "data", parallel = true)
    public Object[][] getData() throws FileNotFoundException {
        List<String> cities = Arrays.asList(FileUtils.readFileFromResources("cities.txt").split("\n"));
        List<String> places = Arrays.asList(FileUtils.readFileFromResources("places.txt").split("\n"));
        List<String> queries = new ArrayList<>();
        for (String city : cities) {
            if (city.trim().isEmpty()) continue;
            for (String place : places) {
                if (place.trim().isEmpty()) continue;
                queries.add(city + " " + place);
            }
        }
        Object[][] objects = new Object[queries.size()][1];
        for (int i = 0; i < queries.size(); i++) {
            String value = queries.get(i);
            objects[i][0] = value;
        }
        return objects;
    }
}
