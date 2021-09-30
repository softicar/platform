package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Function;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AjaxSeleniumLowLevelTestEngineParameters {

	private IAjaxSeleniumLowLevelTestEngine engine;
	private Supplier<WebDriver> webDriverSupplier;
	private Function<IDomNode, WebElement> webElementResolver;
	private Supplier<WebElement> sessionTimeoutDivSupplier;

	public AjaxSeleniumLowLevelTestEngineParameters() {

		this.engine = null;
		this.webDriverSupplier = null;
		this.webElementResolver = null;
		this.sessionTimeoutDivSupplier = null;
	}

	public AjaxSeleniumLowLevelTestEngineParameters setEngine(IAjaxSeleniumLowLevelTestEngine engine) {

		this.engine = engine;
		return this;
	}

	public AjaxSeleniumLowLevelTestEngineParameters setWebDriverSupplier(Supplier<WebDriver> webDriverSupplier) {

		this.webDriverSupplier = webDriverSupplier;
		return this;
	}

	public AjaxSeleniumLowLevelTestEngineParameters setWebElementResolver(Function<IDomNode, WebElement> webElementResolver) {

		this.webElementResolver = webElementResolver;
		return this;
	}

	public AjaxSeleniumLowLevelTestEngineParameters setSessionTimeoutDivSupplier(Supplier<WebElement> sessionTimeoutDivSupplier) {

		this.sessionTimeoutDivSupplier = sessionTimeoutDivSupplier;
		return this;
	}

	public IAjaxSeleniumLowLevelTestEngine getEngine() {

		return engine;
	}

	public Supplier<WebDriver> getWebDriverSupplier() {

		return webDriverSupplier;
	}

	public Function<IDomNode, WebElement> getWebElementResolver() {

		return webElementResolver;
	}

	public Supplier<WebElement> getSessionTimeoutDivSupplier() {

		return sessionTimeoutDivSupplier;
	}
}
