package com.softicar.platform.ajax.testing.selenium.screenshot;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import org.openqa.selenium.WebDriver;

/**
 * A PNG-formatted screenshot, taken from a {@link WebDriver} during Selenium
 * based unit test execution.
 *
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumScreenshot {

	/**
	 * The extension that designates the format of the created screenshots.
	 */
	String EXTENSION = "png";

	/**
	 * The file name of this {@link IAjaxSeleniumScreenshot}.
	 *
	 * @return the file name (never <i>null</i>)
	 */
	String getFileName();

	/**
	 * The PNG-formatted content bytes of this {@link IAjaxSeleniumScreenshot}.
	 *
	 * @return the content bytes (never <i>null</i>)
	 */
	byte[] getBytes();

	/**
	 * Alters the content bytes of this screenshot, by drawing a geometric shape
	 * that marks the coordinates which are represented by the given
	 * {@link AjaxSeleniumTestPoint}.
	 *
	 * @param point
	 *            the {@link AjaxSeleniumTestPoint} to mark (never <i>null</i>)
	 * @return this {@link IAjaxSeleniumScreenshot}
	 */
	IAjaxSeleniumScreenshot drawPointMarker(AjaxSeleniumTestPoint point);
}
