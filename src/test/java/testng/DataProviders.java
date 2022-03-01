package testng;

import org.testng.annotations.DataProvider;
import site.models.User;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.jdiai.tools.PropertyReader.getProperty;

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
        String usersFile = getProperty("users.file");
        List<User> users = User.getFromScv(usersFile);
        Object[][] objects = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            User value = users.get(i);
            objects[i][0] = value;
        }
        return objects;
    }


    @DataProvider(name = "allUsers", parallel = false)
    public Object[][] getAllUsers() throws IOException {
        List<User> users = User.getFromScv("users.csv");
        List<User> users1 = User.getFromScv("users1.csv");
        List<User> users2 = User.getFromScv("users2.csv");
        List<User> users3 = User.getFromScv("users3.csv");
        users.addAll(users1);
        users.addAll(users2);
        users.addAll(users3);
        HashSet<User> userSet = new HashSet<>(users);
        ArrayList<User> list = new ArrayList<>(userSet);
        Object[][] objects = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            User value = list.get(i);
            objects[i][0] = value;
        }
        return objects;
    }
}
