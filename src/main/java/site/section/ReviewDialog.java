package site.section;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.Section;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.Css;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextArea;

public class ReviewDialog extends Section {
    @Css(".business-rating-edit-view__star")
    public WebList rates;
    @Css("textarea")
    public TextArea comment;
    @Css("button._view_primary")
    public Button postButton;
}
