package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import site.SiteYandex;
import testng.DataProviders;
import utils.FileUtils;

import java.util.HashSet;
import java.util.Set;

import static com.epam.jdi.light.elements.init.PageFactory.initSite;
import static com.jdiai.tools.PropertyReader.getProperty;

public class CollectPlacesTest extends BaseTest implements TestsInit {
    static Set<String> urls = new HashSet<>();


    @BeforeClass(alwaysRun = true)
    public void beforeTest() {
        if (Boolean.parseBoolean(getProperty("docker.enabled"))){
            wdm = WebDriverManager.chromedriver()
                    .browserInDocker()
                    .dockerScreenResolution("1920x1080x24");
        } else {
            wdm = WebDriverManager.chromedriver();
        }
        driver = wdm.create();
        WebDriverFactory.useDriver((() -> driver));
        initSite(SiteYandex.class);
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        wdm.quit(driver);
        WebDriverFactory.quit();
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "data")
    public void collectToFileTest(String query) {
        int timeoutQuerySeconds = Integer.parseInt(getProperty("timeout.query.seconds"));
        int itemsCount = Integer.parseInt(getProperty("default.items.count"));
        urls.addAll(collectPlacesUrls(query, timeoutQuerySeconds, itemsCount));
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        FileUtils.writeFileToDownloadsDir("placesUrls.txt", String.join("\n", urls));
    }
}
