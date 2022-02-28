package ru.yandex;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import testng.DataProviders;
import utils.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class CollectPlacesTest extends BaseTest implements TestsInit {
    static Set<String> urls = new HashSet<>();

    @Test(dataProviderClass = DataProviders.class, dataProvider = "data")
    public void collectToFileTest(String query) {
        urls.addAll(collectPlacesUrls(query, 600));
    }

    @AfterClass
    public void afterClass() {
        FileUtils.writeFileToDownloadsDir("placesUrls.txt",String.join("\n", urls));
    }
}
