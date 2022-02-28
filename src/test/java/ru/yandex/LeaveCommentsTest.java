package ru.yandex;

import org.testng.annotations.BeforeClass;
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

    @Test(dataProviderClass = DataProviders.class, dataProvider = "users")
    public void yandexCommentsTest(User user) {
        login(user);
//        for (String placeHref : uniqPlaces) {
//            postComment(placeHref, "Good place!");
//        }

        String debugUrl = uniqPlaces.stream().findFirst().get();
        postComment(debugUrl, "Good place!");

    }

}
