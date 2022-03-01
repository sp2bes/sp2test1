package site.section;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.Section;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.Css;
import com.epam.jdi.light.ui.html.elements.common.Button;

public class ReviewSection extends Section {

    @Css(".business-rating-edit-view__star")
    public WebList rates;
    @Css(".business-review-view__delete")
    public Button deleteRate;
}
