package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import site.SiteYandex;
import testng.TestNGListener;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.elements.init.PageFactory.initSite;
import static com.epam.jdi.light.settings.WebSettings.logger;

@Listeners(TestNGListener.class)
public interface TestsInit {
    @BeforeSuite(alwaysRun = true)
    static void setUpSuite() {
        killAllSeleniumDrivers();
        logger.info("Run Tests");
    }

    @AfterSuite(alwaysRun = true)
    static void tearDownSuite() {
        WebDriverFactory.quit();
        killAllSeleniumDrivers();
    }
}
