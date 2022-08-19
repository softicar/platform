package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestItem;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteIndicatorType;
import java.util.List;
import java.util.Optional;

public interface IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension {

	List<String> getAutoCompletePopupItemNames();

	Optional<Integer> getAutoCompletePopupSelectedItemIndex();

	boolean isAutoCompletePopupDisplayed();

	boolean isAutoCompleteIndicatorDisplayed(DomAutoCompleteIndicatorType indicatorType);

	boolean isAutoCompleteModalDivDisplayed();

	boolean isAutoCompleteItemPlaceholderDisplayed();

	boolean isAutoCompleteMoreItemsInfoDisplayed();

	void clickAutoCompletePopupItem(int index);

	void clickAutoCompleteItem(IAjaxSeleniumTestDomAutoCompleteTestItem item);

	void clickAutoCompleteModalDiv();

	void waitForAutoCompletePopup();

	void waitForAutoCompletePopupToHide();

	void waitForIndicatorToHide(DomAutoCompleteIndicatorType indicatorType);
}
