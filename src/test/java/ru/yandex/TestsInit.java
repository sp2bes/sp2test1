package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import testng.TestNGListener;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.settings.WebSettings.logger;

@Listeners(TestNGListener.class)
public interface TestsInit {
    @BeforeSuite(alwaysRun = true)
    default void setUpSuite() {
        killAllSeleniumDrivers();
        logger.info("Run Tests");
    }

    @AfterSuite(alwaysRun = true)
    default void tearDownSuite() {
        WebDriverFactory.quit();
        killAllSeleniumDrivers();
    }
}
