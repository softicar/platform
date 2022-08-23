package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestValue;
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

class AjaxSeleniumLowLevelTestEngineAutoCompleteExtension implements IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension {

	private final Supplier<WebDriver> webDriverSupplier;

	public AjaxSeleniumLowLevelTestEngineAutoCompleteExtension(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
	}

	@Override
	public List<String> getAutoCompletePopupValueNames() {

		return getAutoCompleteValueWebElements()//
			.stream()
			.map(WebElement::getText)
			.collect(Collectors.toList());
	}

	@Override
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

	@Override
	public boolean isAutoCompletePopupDisplayed() {

		return getAutoCompletePopup().isPresent();
	}

	@Override
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

	@Override
	public boolean isAutoCompleteBackdropDisplayed() {

		return !webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.isEmpty();
	}

	@Override
	public boolean isAutoCompleteValuePlaceholderDisplayed() {

		return getAutoCompleteValuePlaceholderElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	@Override
	public boolean isAutoCompleteMoreValuesInfoDisplayed() {

		return getAutoCompleteMoreValuesInfoElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	@Override
	public void clickAutoCompletePopupValue(int index) {

		getAutoCompleteValueWebElements().get(index).click();
	}

	@Override
	public void clickAutoCompleteValue(IAjaxSeleniumTestDomAutoCompleteTestValue value) {

		findValue(value).get().click();
	}

	@Override
	public void clickAutoCompleteBackdrop() {

		webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.stream()
			.findFirst()
			.ifPresent(WebElement::click);
	}

	private Optional<WebElement> findValue(IAjaxSeleniumTestDomAutoCompleteTestValue value) {

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
