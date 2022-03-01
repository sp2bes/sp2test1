package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import com.google.common.collect.Lists;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import site.SiteYandex;
import site.models.User;
import testng.DataProviders;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.jdi.light.elements.init.PageFactory.initSite;
import static com.jdiai.tools.PropertyReader.getProperty;

public class LeaveCommentsTest extends BaseTest implements TestsInit {
    static Set<String> uniqPlaces = new HashSet<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() throws InterruptedException {
        int rnd = new Random().nextInt(10);
        Thread.sleep(rnd * 1000);

        boolean dockerEnabled = Boolean.parseBoolean(getProperty("docker.enabled"));
        if (dockerEnabled) {
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
    public void yandexCommentsTest(User user) {
        login(user);

        for (String placeHref : uniqPlaces) {
            try {
                postComment(placeHref);
            } catch (Throwable ignore) {
            }
        }

    }

    @Test
    public void yandexCommentsTest() throws FileNotFoundException {
        User user = new User("romatrusov2", "rASSiaVperde123");
        login(user);

        for (String placeHref : uniqPlaces) {
            postComment(placeHref);
        }

    }

    @Test
    public void yandexComments1Test() throws FileNotFoundException {
        User user = new User("seregeivesely", "Sv01234@Sv");
        login(user);

        for (String placeHref : uniqPlaces) {
            postComment(placeHref);
        }

    }

    @Test
    public void yandexComments2Test() throws FileNotFoundException {
        User user = new User("ivanhuyarin", "IvH12345@54321HiV");
        login(user);
        List<String> reversed = Lists.reverse(new ArrayList<>(uniqPlaces));
        for (String placeHref : reversed) {
            postComment(placeHref);
        }

    }

}
