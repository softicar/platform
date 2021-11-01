package com.softicar.platform.ajax.testing.selenium.screenshot;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;

class AjaxSeleniumDummyScreenshot implements IAjaxSeleniumScreenshot {

	@Override
	public String getFileName() {

		return "";
	}

	@Override
	public byte[] getBytes() {

		return new byte[] {};
	}

	@Override
	public IAjaxSeleniumScreenshot drawPointMarker(AjaxSeleniumTestPoint point) {

		return this;
	}
}
