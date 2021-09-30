package com.softicar.platform.ajax.testing.selenium.web.driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

public class AjaxSeleniumWebDriverHistory {

	private final Map<WebDriver, Collection<Description>> webDrivers;

	public AjaxSeleniumWebDriverHistory() {

		this.webDrivers = new WeakHashMap<>();
	}

	public synchronized void registerUsage(WebDriver webDriver, Description description) {

		webDrivers.computeIfAbsent(webDriver, dummy -> new ArrayList<>()).add(description);
	}

	public synchronized Collection<Description> getUsageList(WebDriver webDriver) {

		return new ArrayList<>(webDrivers.getOrDefault(webDriver, Collections.emptyList()));
	}
}
