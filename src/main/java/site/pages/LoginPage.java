package site.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Title;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.Css;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextField;

@Url("https://passport.yandex.ru/auth") @Title("Login Page")
public class LoginPage extends WebPage {
    @Css("[name='login']")
    public TextField loginInput;
    @Css("[id='passp:sign-in']")
    public Button loginButton;

    @Css("[id='passp-field-passwd']")
    public TextField password;

    @Css("[data-t='phone_skip'] button")
    public Button phoneSkip;

}
