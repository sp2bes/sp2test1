package ru.yandex;

import com.epam.jdi.light.driver.WebDriverFactory;
import com.jdiai.tools.Timer;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import site.SiteYandex;
import site.models.User;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.elements.init.PageFactory.initSite;
import static site.SiteYandex.loginPage;
import static site.SiteYandex.mapsPage;

public class BaseTest {
    WebDriver driver;
    static volatile WebDriverManager wdm = null;
    String[] comments = null;

    @BeforeClass(alwaysRun = true)
    public void beforeTest() {
        wdm = WebDriverManager.chromedriver()
                .browserInDocker()
                .dockerScreenResolution("1920x1080x24");
        driver = wdm.create();
        WebDriverFactory.useDriver((() -> driver));
        initSite(SiteYandex.class);
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        wdm.quit(driver);
        WebDriverFactory.quit();
    }

    @AfterSuite(alwaysRun = true)
    static void stopAll() {
        wdm.quit();
    }

    protected void postComment(String url) throws FileNotFoundException {
        String[] comments = getComments();
        postComment(url, getRandomComment(comments));
    }

    private String[] getComments() throws FileNotFoundException {
        if (null == comments) {
            comments = FileUtils.readFileFromResources("comments.txt").split("\n");
        }
        return comments;
    }

    public static String getRandomComment(String[] comments) {
        int rnd = new Random().nextInt(comments.length);
        return comments[rnd];
    }

    @Step
    protected void postComment(String url, String comment) {
        mapsPage.driver().get(url);
        mapsPage.reviewSection.shouldBe().displayed().enabled();
        mapsPage.reviewSection.show();
        mapsPage.reviewSection.hover();
        mapsPage.reviewSection.rates.shouldBe().displayed();

        int size = mapsPage.reviewSection.rates.size();
        if (size < 1 && mapsPage.reviewSection.deleteRate.isDisplayed()) return; //skip comment if your review exists

        mapsPage.reviewSection.rates.get(size - 1).click();

        mapsPage.reviewDialog.shouldBe().displayed();
        mapsPage.reviewDialog.rates.shouldBe().displayed();
        mapsPage.reviewDialog.comment.setValue(comment);
        mapsPage.reviewDialog.postButton.click();
        mapsPage.reviewDialog.postButton.shouldBe().disappear();
    }

    protected Set<String> collectPlacesUrls(String city, String placeType, int timeInSecondsToCollect) {
        return collectPlacesUrls(city + " " + placeType, timeInSecondsToCollect);
    }

    @Step
    protected Set<String> collectPlacesUrls(String query, int timeInSecondsToCollect) {
        return collectPlacesUrls(query, timeInSecondsToCollect, 25);
    }

    @Step
    protected Set<String> collectPlacesUrls(String query, int timeInSecondsToCollect, int itemsCount) {
        Set<String> uniqPlaces = new HashSet<>();
        mapsPage.open();
        mapsPage.search.shouldBe().displayed();
        mapsPage.search.input(query + Keys.ENTER);
        mapsPage.results.clear();
        mapsPage.results.shouldBe().displayed();
        Timer timer = new Timer(timeInSecondsToCollect * 1000);
        timer.wait(() -> {
            int size = mapsPage.results.size();
            mapsPage.results.get(size).hover();
            mapsPage.results.get(size).show();
            mapsPage.results.forEach(uiElement -> uniqPlaces.add(uiElement.getAttribute("href")));
            int sizeAfter = mapsPage.results.size();
            return itemsCount < sizeAfter || sizeAfter <= size;
        });
        return uniqPlaces;
    }

    protected void login(User user) {
        login(user.getLogin(), user.getPassword());
    }

    @Step
    protected void login(String login, String password) {
        loginPage.open();
        loginPage.loginInput.input(login);
        loginPage.loginButton.click();
        loginPage.password.shouldBe().displayed();
        loginPage.password.input(password);
        loginPage.loginButton.click();
        loginPage.password.shouldBe().disappear();

        if (loginPage.phoneSkip.isExist()) {
            loginPage.phoneSkip.shouldBe().displayed();
            loginPage.phoneSkip.click();
            loginPage.phoneSkip.shouldBe().disappear();
        }
    }
}
