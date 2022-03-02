package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        if (dockerEnabled) {
            wdm = WebDriverManager.chromedriver()
                    .browserInDocker()
                    .dockerScreenResolution("1920x1080x24");
            driver = wdm.create();
            WebDriverFactory.useDriver((() -> driver));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        if (dockerEnabled) {
            wdm.quit(driver);
        }
        WebDriverFactory.quit();
    }

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        Collections.addAll(uniqPlaces, FileUtils.readFileFromDownloads("placesUrls.txt").split("\n"));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "users")
    public void yandexCommentsTest(User user) throws FileNotFoundException {
        login(user);
//        postComment("https://yandex.ru/maps/org/restoran_don/101966470409/");

        for (String placeHref : uniqPlaces) {
            postComment(placeHref);
        }

    }

}
