package com.softicar.platform.ajax.testing.selenium.screenshot;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import java.util.Objects;

class AjaxSeleniumScreenshot implements IAjaxSeleniumScreenshot {

	private final String fileName;
	private byte[] bytes;

	public AjaxSeleniumScreenshot(String fileName, byte[] bytes) {

		this.fileName = fileName;
		this.bytes = Objects.requireNonNull(bytes);
	}

	@Override
	public String getFileName() {

		return fileName;
	}

	@Override
	public byte[] getBytes() {

		return bytes;
	}

	@Override
	public IAjaxSeleniumScreenshot drawPointMarker(AjaxSeleniumTestPoint point) {

		this.bytes = new AjaxSeleniumScreenshotDecorator().drawPointMarker(bytes, point);
		return this;
	}
}
