package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteInputIndicator;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestItem;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

class AjaxSeleniumLowLevelTestEngineAutoCompleteExtension implements IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension {

	private static final Duration AUTO_COMPLETE_TIMEOUT = Duration.ofSeconds(10);
	private static final Duration INDICATOR_TIMEOUT = Duration.ofSeconds(10);

	private final IAjaxSeleniumLowLevelTestEngine engine;
	private final Supplier<WebDriver> webDriverSupplier;

	public AjaxSeleniumLowLevelTestEngineAutoCompleteExtension(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.engine = parameters.getEngine();
		this.webDriverSupplier = parameters.getWebDriverSupplier();
	}

	@Override
	public List<String> getAutoCompletePopupItemNames() {

		return getAutoCompleteValueWebElements()//
			.stream()
			.map(WebElement::getText)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Integer> getAutoCompletePopupSelectedItemIndex() {

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
			throw new RuntimeException("Encountered more than one selected item.");
		}
	}

	@Override
	public boolean isAutoCompletePopupDisplayed() {

		return getAutoCompletePopup().map(WebElement::isDisplayed).orElse(false);
	}

	@Override
	public boolean isAutoCompleteIndicatorDisplayed(IAjaxSeleniumTestDomAutoCompleteInputIndicator indicator) {

		return webDriverSupplier//
			.get()
			.findElements(By.className(indicator.getCssClass()))
			.stream()
			.findFirst()
			.isPresent();
	}

	@Override
	public boolean isAutoCompleteModalDivDisplayed() {

		return !webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.isEmpty();
	}

	@Override
	public boolean isAutoCompleteItemPlaceholderDisplayed() {

		return getAutoCompleteItemPlaceholderElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	@Override
	public boolean isAutoCompleteMoreItemsInfoDisplayed() {

		return getAutoCompleteMoreItemsInfoElement()//
			.map(WebElement::isDisplayed)
			.orElse(false);
	}

	@Override
	public void clickAutoCompletePopupItem(int index) {

		getAutoCompleteValueWebElements().get(index).click();
	}

	@Override
	public void clickAutoCompleteItem(IAjaxSeleniumTestDomAutoCompleteTestItem item) {

		findItem(item).get().click();
	}

	@Override
	public void clickAutoCompleteModalDiv() {

		webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP.getName()))
			.stream()
			.findFirst()
			.ifPresent(WebElement::click);
	}

	@Override
	public void waitForAutoCompletePopup() {

		new FluentWait<>(0)//
			.withTimeout(AUTO_COMPLETE_TIMEOUT)
			.withMessage("Timeout while waiting for auto complete popup to appear.")
			.until(dummy -> isAutoCompletePopupDisplayed());
	}

	@Override
	public void waitForAutoCompletePopupToHide() {

		new FluentWait<>(0)//
			.withTimeout(AUTO_COMPLETE_TIMEOUT)
			.withMessage("Timeout while waiting for auto complete popup to disappear.")
			.until(dummy -> !isAutoCompletePopupDisplayed());

		// In case the input listens to change, hiding the pop-up will cause an event - so we must wait here.
		engine.waitForServer();
	}

	@Override
	public void waitForIndicatorToHide(IAjaxSeleniumTestDomAutoCompleteInputIndicator indicator) {

		new FluentWait<>(0)//
			.withTimeout(INDICATOR_TIMEOUT)
			.withMessage("Timeout while waiting for auto complete indicator to disappear.")
			.until(dummy -> !isAutoCompleteIndicatorDisplayed(indicator));
	}

	private Optional<WebElement> findItem(IAjaxSeleniumTestDomAutoCompleteTestItem item) {

		return getAutoCompleteValueWebElements()//
			.stream()
			.filter(it -> it.getText().contains(item.getName()))
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

	private Optional<WebElement> getAutoCompleteItemPlaceholderElement() {

		return webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_NO_VALUES.getName()))
			.stream()
			.findFirst();
	}

	private Optional<WebElement> getAutoCompleteMoreItemsInfoElement() {

		return webDriverSupplier//
			.get()
			.findElements(By.className(DomElementsCssClasses.DOM_AUTO_COMPLETE_MORE_VALUES.getName()))
			.stream()
			.findFirst();
	}
}
