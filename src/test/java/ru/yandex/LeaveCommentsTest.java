package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import site.User;
import testng.DataProviders;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class LeaveCommentsTest extends BaseTest implements TestsInit {
    Set<String> uniqPlaces = new HashSet<>();

    @BeforeClass
    public void setUp() throws FileNotFoundException {
        String[] urls = FileUtils.readFileFromDownloads("placesUrls.txt").split("\n");
        Collections.addAll(uniqPlaces, urls);
    }

    @AfterMethod
    public void tearDown() {
        WebDriverFactory.quit();
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "users")
    public void yandexCommentsTest(User user) throws FileNotFoundException {
        login(user);
        postComment(uniqPlaces.stream().findFirst().get());
//
//        for (String placeHref : uniqPlaces) {
//            postComment(placeHref);
//        }

    }

}
