package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import site.SiteYandex;
import site.models.User;
import testng.DataProviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.jdi.light.elements.init.PageFactory.initSite;
import static com.jdiai.tools.PropertyReader.getProperty;

public class VerifyUsersTest extends BaseTest implements TestsInit {
    static List<User> correctUsers = new ArrayList<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        boolean dockerEnabled = Boolean.parseBoolean(getProperty("docker.enabled"));
        dockerEnabled = false;
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

    @AfterClass
    public void afterClass() throws IOException {
        User.toCsv("users_correct.csv", correctUsers);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "allUsers")
    public void verifyUser(User user) throws IOException {
        login(user);
        correctUsers.add(user);
    }

}
