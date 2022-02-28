package ru.yandex;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import testng.DataProviders;
import utils.FileUtils;

import java.util.HashSet;
import java.util.Set;

import static com.jdiai.tools.PropertyReader.getProperty;

public class CollectPlacesTest extends BaseTest implements TestsInit {
    static Set<String> urls = new HashSet<>();

    @Test(dataProviderClass = DataProviders.class, dataProvider = "data")
    public void collectToFileTest(String query) {
        int timeoutQuerySeconds = Integer.parseInt(getProperty("timeout.query.seconds"));
        urls.addAll(collectPlacesUrls(query, timeoutQuerySeconds));
    }

    @AfterMethod
    public void after() {
        FileUtils.writeFileToDownloadsDir("placesUrls.txt",String.join("\n", urls));
    }
}
