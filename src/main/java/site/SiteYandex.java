package site;

import com.epam.jdi.light.elements.pageobjects.annotations.JSite;
import site.pages.LoginPage;
import site.pages.MapsPage;

@JSite("https://yandex.ru/")
public class SiteYandex {
    public static LoginPage loginPage;
    public static MapsPage mapsPage;
}
