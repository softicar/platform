package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineViewport;
import java.util.function.Supplier;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

class AjaxSeleniumLowLevelTestEngineViewport implements IAjaxSeleniumLowLevelTestEngineViewport {

	private final Supplier<WebDriver> webDriverSupplier;
	private final AjaxSeleniumLowLevelTestJavascriptExecutor javascriptExecutor;

	public AjaxSeleniumLowLevelTestEngineViewport(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
		this.javascriptExecutor = new AjaxSeleniumLowLevelTestJavascriptExecutor(webDriverSupplier);
	}

	@Override
	public AjaxSeleniumTestSegment getViewportSize() {

		return new AjaxSeleniumTestSegment(getViewportWidth(), getViewportHeight());
	}

	@Override
	public void setViewportSize(int width, int height) {

		Dimension dimension = getWindow().getSize();
		AjaxSeleniumTestSegment windowSize = new AjaxSeleniumTestSegment(dimension.getWidth(), dimension.getHeight());
		AjaxSeleniumTestSegment viewportSize = getViewportSize();
		int extraWidth = windowSize.getWidth() - viewportSize.getWidth();
		int extraHeight = windowSize.getHeight() - viewportSize.getHeight();
		getWindow().setSize(new org.openqa.selenium.Dimension(width + extraWidth, height + extraHeight));
	}

	@Override
	public void scrollTo(int x, int y) {

		javascriptExecutor.execute("window.scrollTo(%s,%s)", x, y);
	}

	private Window getWindow() {

		return webDriverSupplier.get().manage().window();
	}

	private int getViewportWidth() {

		Long width = (Long) javascriptExecutor.execute("return window.innerWidth;");
		return width.intValue();
	}

	private int getViewportHeight() {

		Long height = (Long) javascriptExecutor.execute("return window.innerHeight;");
		return height.intValue();
	}
}
