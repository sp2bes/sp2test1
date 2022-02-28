package testng;

import org.testng.annotations.DataProvider;
import site.User;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    @DataProvider(name = "data", parallel = true)
    public Object[][] getData() throws FileNotFoundException {
        String[] cities = FileUtils.readFileFromResources("cities.txt").split("\n");
        String[] places = FileUtils.readFileFromResources("places.txt").split("\n");
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

    @DataProvider(name = "users", parallel = true)
    public Object[][] getUsers() throws IOException {
        List<User> users = User.getFromScv("users.csv");
        Object[][] objects = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            User value = users.get(i);
            objects[i][0] = value;
        }
        return objects;
    }
}
