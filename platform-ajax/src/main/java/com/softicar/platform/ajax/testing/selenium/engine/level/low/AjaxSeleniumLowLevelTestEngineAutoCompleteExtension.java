package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.elements.IAjaxSeleniumAutoCompleteTestValue;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteIndicatorType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AjaxSeleniumLowLevelTestEngineAutoCompleteExtension {

	private final Supplier<WebDriver> webDriverSupplier;

	AjaxSeleniumLowLevelTestEngineAutoCompleteExtension(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
	}

	public List<String> getAutoCompletePopupValueNames() {

		return getAutoCompleteValueWebElements()//
			.stream()
			.map(WebElement::getText)
			.collect(Collectors.toList());
	}

	public Optional<Integer> getAutoCompletePopupSelectedValueIndex() {

		List<WebElement> available = getAutoCompleteValueWebElements();
		List<WebElement> selected = getAutoCompletePopup()//
			.filter(WebElement::isDisplayed)
			.map(it -> it.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_SELECTED_VALUE.getName())))
			.orElse(Collections.emptyList());
		if (selected.isEmpty()) {
			return Optional.empty();
		} else if (selected.size() == 1) {
			WebElement selectedElement = selected.iterator().next();
			return Optional.of(available.indexOf(selectedElement));
		} else {
			throw new RuntimeException("Encountered more than one selected value.");
		}
	}

	public boolean isAutoCompletePopupDisplayed() {

		return getAutoCompletePopup().isPresent();
	}

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

	public boolean isAutoCompleteIndicatorDisplayed(DomAutoCompleteIndicatorType indicatorType) {

		if (indicatorType != null) {
			return webDriverSupplier//
				.get()
				.findElements(By.className(indicatorType.getCssClass().getName()))
				.stream()
				.findFirst()
				.isPresent();
		} else {
			return webDriverSupplier//
				.get()
				.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR.getName()))
				.stream()
				.findFirst()
				.isEmpty();
		}
	}

	public boolean isAutoCompleteBackdropDisplayed() {

		return !webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.isEmpty();
	}

	public boolean isAutoCompleteValuePlaceholderDisplayed() {

		return getAutoCompleteValuePlaceholderElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	public boolean isAutoCompleteMoreValuesInfoDisplayed() {

		return getAutoCompleteMoreValuesInfoElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	public void clickAutoCompletePopupValue(int index) {

		getAutoCompleteValueWebElements().get(index).click();
	}

	public void clickAutoCompleteValue(IAjaxSeleniumAutoCompleteTestValue value) {

		findValue(value).get().click();
	}

	public void clickAutoCompleteBackdrop() {

		webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.stream()
			.findFirst()
			.ifPresent(WebElement::click);
	}

	private Optional<WebElement> findValue(IAjaxSeleniumAutoCompleteTestValue value) {

		return getAutoCompleteValueWebElements()//
			.stream()
			.filter(it -> it.getText().contains(value.getName()))
			.findFirst();
	}

	private List<WebElement> getAutoCompleteValueWebElements() {

		Optional<WebElement> popup = getAutoCompletePopup();
		if (popup.isPresent()) {
			return popup//
				.get()
				.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_VALUE.getName()))
				.stream()
				.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	private Optional<WebElement> getAutoCompletePopup() {

		return webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_POPUP.getName()))
			.stream()
			.findFirst();
	}

	private Optional<WebElement> getAutoCompleteValuePlaceholderElement() {

		return webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_NO_VALUES.getName()))
			.stream()
			.findFirst();
	}

	private Optional<WebElement> getAutoCompleteMoreValuesInfoElement() {

		return webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_MORE_VALUES.getName()))
			.stream()
			.findFirst();
	}
}
