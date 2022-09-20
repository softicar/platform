package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestArea;
import java.time.Duration;
import java.util.function.Supplier;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Facilitates controlling the viewport of a UI-under-test.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumLowLevelTestEngineViewport {

	private final Supplier<WebDriver> webDriverSupplier;
	private final AjaxSeleniumLowLevelTestJavascriptExecutor javascriptExecutor;

	AjaxSeleniumLowLevelTestEngineViewport(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
		this.javascriptExecutor = new AjaxSeleniumLowLevelTestJavascriptExecutor(webDriverSupplier);
	}

	public AjaxSeleniumTestArea getViewportSize() {

		return new AjaxSeleniumTestArea(getViewportWidth(), getViewportHeight());
	}

	public void setViewportSize(int width, int height) {

		var viewportSizeDesired = new AjaxSeleniumTestArea(width, height);
		var viewportSizeBefore = getViewportSize();

		if (!viewportSizeBefore.equals(viewportSizeDesired)) {
			Dimension windowDimension = getWindow().getSize();
			AjaxSeleniumTestArea windowSize = new AjaxSeleniumTestArea(windowDimension.getWidth(), windowDimension.getHeight());
			int extraWidth = windowSize.getWidth() - viewportSizeBefore.getWidth();
			int extraHeight = windowSize.getHeight() - viewportSizeBefore.getHeight();

			getWindow().setSize(new Dimension(width + extraWidth, height + extraHeight));

			// Wait for the window size to actually change.
			// Inspired by https://stackoverflow.com/a/40242082
			new WebDriverWait(webDriverSupplier.get(), Duration.ofSeconds(10))//
				.until(driver -> !getViewportSize().equals(viewportSizeBefore));
		}
	}

	public void scrollTo(int x, int y) {

		javascriptExecutor.execute("window.scrollTo(%s,%s)", x, y);
	}

	private Window getWindow() {

		return webDriverSupplier.get().manage().window();
	}

	private int getViewportWidth() {

		return ((Long) javascriptExecutor.execute("return window.innerWidth;")).intValue();
	}

	private int getViewportHeight() {

		return ((Long) javascriptExecutor.execute("return window.innerHeight;")).intValue();
	}
}
