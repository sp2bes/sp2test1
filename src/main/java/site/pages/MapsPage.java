package site.pages;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.Css;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextField;
import site.section.ReviewDialog;
import site.section.ReviewSection;

@Url("/maps/")
public class MapsPage extends WebPage {

    @Css(".search-form-view__input input")
    public TextField search;

    @Css(".business-card-view__overview")
    public ReviewSection reviewSection;

    @Css(".business-review-view__delete")
    public Button deleteRate;

    @Css("._materialized .business-rating-edit-view__star")
    public WebList rates;

    @Css("._name_reviews")
    public Button reviewTab;

    @Css(".search-snippet-view__link-overlay")
    public WebList results;

    @Css("div.dialog")
    public ReviewDialog reviewDialog;

}
