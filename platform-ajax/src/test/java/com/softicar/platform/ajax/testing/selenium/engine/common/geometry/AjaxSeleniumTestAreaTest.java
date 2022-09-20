package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AjaxSeleniumTestAreaTest extends AbstractTest {

	private final AjaxSeleniumTestArea area;

	public AjaxSeleniumTestAreaTest() {

		this.area = new AjaxSeleniumTestArea(30, 40);
	}

	@Test
	public void testGetWidth() {

		assertEquals(30, area.getWidth());
	}

	@Test
	public void testGetHeight() {

		assertEquals(40, area.getHeight());
	}

	@Test
	public void testEquals() {

		AjaxSeleniumTestArea other = new AjaxSeleniumTestArea(30, 40);
		assertTrue(area.equals(other));
	}

	@Test
	public void testEqualsWithDifferentWidth() {

		AjaxSeleniumTestArea other = new AjaxSeleniumTestArea(99, 40);
		assertFalse(area.equals(other));
	}

	@Test
	public void testEqualsWithDifferentHeight() {

		AjaxSeleniumTestArea other = new AjaxSeleniumTestArea(30, 99);
		assertFalse(area.equals(other));
	}
}
