package ru.yandex;

import com.epam.jdi.light.common.ElementArea;
import com.jdiai.tools.Timer;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import site.models.User;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static site.SiteYandex.loginPage;
import static site.SiteYandex.mapsPage;

public class BaseTest {
    static volatile WebDriverManager wdm = null;
    WebDriver driver;
    String[] comments = null;

    @AfterSuite(alwaysRun = true)
    static void stopAll() {
        wdm.quit();
    }

    public static String getRandomComment(String[] comments) {
        int rnd = new Random().nextInt(comments.length);
        return comments[rnd].replace("\\n", "\n");
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

    @Step
    protected void postComment(String url, String comment) {
        mapsPage.driver().get(url);
        mapsPage.reviewTab.shouldBe().displayed().enabled();
        mapsPage.reviewTab.click();

        Timer timer = new Timer(4000L);
        boolean isRated = timer.wait(() -> mapsPage.deleteRate.isDisplayed());

        if (isRated) return; //skip comment if your review exists
        mapsPage.rates.shouldBe().displayed().enabled();
        mapsPage.rates.show();

        int size = mapsPage.rates.size();
        mapsPage.rates.get(size - 1).click(ElementArea.JS);

        mapsPage.reviewDialog.shouldBe().displayed();
        mapsPage.reviewDialog.rates.shouldBe().displayed();
        mapsPage.reviewDialog.comment.setValue(comment);
        mapsPage.reviewDialog.postButton.click();
        try {
            mapsPage.reviewDialog.postButton.click(ElementArea.JS);
        } catch (Throwable ignore) {
        }
        try {
            boolean displayed = mapsPage.reviewDialog.postButton.isDisplayed();
            if (displayed)
                mapsPage.reviewDialog.postButton.click(ElementArea.JS);
        } catch (Throwable ignore) {
        }

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
        enterSearchQuery(query);
        scrollAndCollectURLs(timeInSecondsToCollect, itemsCount, uniqPlaces);
        Allure.addAttachment(query.replace(" ", "_").trim().toLowerCase() + ".txt", String.join("\n", uniqPlaces));
        return uniqPlaces;
    }

    @Step("Scroll results and collect URLs")
    private void scrollAndCollectURLs(int timeInSecondsToCollect, int itemsCount, Set<String> uniqPlaces) {
        mapsPage.results.clear();
        mapsPage.results.shouldBe().displayed();
        Timer timer = new Timer(timeInSecondsToCollect * 1000L);
        timer.wait(() -> {
            int size = mapsPage.results.size();
            mapsPage.results.get(size).hover();
            mapsPage.results.get(size).show();
            mapsPage.results.forEach(uiElement -> uniqPlaces.add(uiElement.getAttribute("href")));
            int sizeAfter = mapsPage.results.size();
            return itemsCount < sizeAfter || sizeAfter <= size;
        });
    }

    @Step
    private void enterSearchQuery(String query) {
        mapsPage.search.shouldBe().displayed();
        mapsPage.search.core().click();
        mapsPage.search.clear();
        mapsPage.search.input(query + Keys.ENTER);
    }

    protected void login(User user) {
        login(user.getLogin(), user.getPassword());
    }

    @Step
    protected void login(String login, String password) {
        loginPage.open();
        loginPage.loginInput.shouldBe().displayed();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginPage.loginInput.clear();
        loginPage.loginInput.core().click();
        loginPage.loginInput.input(login);
        loginPage.loginButton.click();
        loginPage.password.shouldBe().displayed();
        loginPage.password.input(password);
        loginPage.loginButton.click();
        loginPage.password.shouldBe().disappear();

        Timer timer = new Timer(2000L);
        boolean needSkipPhone = timer.wait(() -> {
            loginPage.phoneSkip.isDisplayed();
        });

        if (needSkipPhone) {
            try {
                loginPage.phoneSkip.click(ElementArea.JS);
                loginPage.phoneSkip.shouldBe().disappear();
            }catch (Throwable ignore){

            }

        }
    }
}
