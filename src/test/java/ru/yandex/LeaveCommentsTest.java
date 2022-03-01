package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;
import site.SiteYandex;
import site.models.User;
import testng.DataProviders;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.epam.jdi.light.elements.init.PageFactory.initSite;

public class LeaveCommentsTest extends BaseTest implements TestsInit {
    static Set<String> uniqPlaces = new HashSet<>();


    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
//        wdm = WebDriverManager.chromedriver()
//                .browserInDocker()
//                .dockerScreenResolution("1920x1080x24");
        wdm = WebDriverManager.chromedriver();
//                .browserInDocker()
//                .dockerScreenResolution("1920x1080x24");
        driver = wdm.create();
        WebDriverFactory.useDriver((() -> driver));
        initSite(SiteYandex.class);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        wdm.quit(driver);
        WebDriverFactory.quit();
    }

    @BeforeClass
    public static void beforeClass() throws FileNotFoundException {
        String[] urls = FileUtils.readFileFromDownloads("placesUrls.txt").split("\n");
        Collections.addAll(uniqPlaces, urls);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "users")
    public void yandexCommentsTest(User user) throws FileNotFoundException {
        login(user);
//        postComment(uniqPlaces.stream().findFirst().get());

        for (String placeHref : uniqPlaces) {
            postComment(placeHref);
        }

    }

}
