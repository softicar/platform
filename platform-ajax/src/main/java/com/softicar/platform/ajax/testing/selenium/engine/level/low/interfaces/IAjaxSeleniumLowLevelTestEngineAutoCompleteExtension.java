package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestItem;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteIndicatorType;
import java.util.List;
import java.util.Optional;

public interface IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension {

	List<String> getAutoCompletePopupItemNames();

	Optional<Integer> getAutoCompletePopupSelectedItemIndex();

	boolean isAutoCompletePopupDisplayed();

	/**
	 * Determines whether an indicator of the given
	 * {@link DomAutoCompleteIndicatorType} is displayed.
	 * <p>
	 * If <i>null</i> is given, it determines whether <b>no</b> indicator is
	 * displayed.
	 *
	 * @param indicatorType
	 *            the indicator type to check (may be <i>null</i>)
	 * @return <i>true</i> if non-<i>null</i> is given and an indicator of the
	 *         given type is displayed; <i>true</i> if <i>null</i> is given and
	 *         no indicator is displayed; <i>false</i> otherwise
	 */
	boolean isAutoCompleteIndicatorDisplayed(DomAutoCompleteIndicatorType indicatorType);

	boolean isAutoCompleteBackdropDisplayed();

	boolean isAutoCompleteItemPlaceholderDisplayed();

	boolean isAutoCompleteMoreItemsInfoDisplayed();

	void clickAutoCompletePopupItem(int index);

	void clickAutoCompleteItem(IAjaxSeleniumTestDomAutoCompleteTestItem item);

	void clickAutoCompleteBackdrop();

	void waitForAutoCompletePopup();

	void waitForAutoCompletePopupToHide();

	void waitForIndicatorToHide(DomAutoCompleteIndicatorType indicatorType);
}
