package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;

/**
 * Facilitates controlling the viewport of a UI-under-test.
 *
 * @see IAjaxSeleniumLowLevelTestEngine
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumLowLevelTestEngineViewport {

	AjaxSeleniumTestSegment getViewportSize();

	void setViewportSize(int width, int height);

	void scrollTo(int x, int y);
}
