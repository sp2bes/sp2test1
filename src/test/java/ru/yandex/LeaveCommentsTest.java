package ru.yandex;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import site.models.User;
import testng.DataProviders;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LeaveCommentsTest extends BaseTest implements TestsInit {
    static Set<String> uniqPlaces = new HashSet<>();

    @BeforeClass
    public static void beforeClass() throws FileNotFoundException {
        String[] urls = FileUtils.readFileFromDownloads("placesUrls.txt").split("\n");
        Collections.addAll(uniqPlaces, urls);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "users")
    public void yandexCommentsTest(User user) throws FileNotFoundException {
        login(user);
        for (String placeHref : uniqPlaces) {
            postComment(placeHref);
        }

    }

}
