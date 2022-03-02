package site;

import com.epam.jdi.light.elements.pageobjects.annotations.JSite;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import site.pages.LoginPage;
import site.pages.MapsPage;

@JSite("https://yandex.ru/")
public class SiteYandex {
    @Url("https://passport.yandex.ru/auth")
    public static LoginPage loginPage;
    @Url("/maps/")
    public static MapsPage mapsPage;
}
