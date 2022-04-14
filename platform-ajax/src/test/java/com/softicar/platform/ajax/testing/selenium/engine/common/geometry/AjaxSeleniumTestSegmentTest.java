package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AjaxSeleniumTestSegmentTest extends AbstractTest {

	private final AjaxSeleniumTestSegment extent;

	public AjaxSeleniumTestSegmentTest() {

		this.extent = new AjaxSeleniumTestSegment(30, 40);
	}

	@Test
	public void testGetWidth() {

		assertEquals(30, extent.getWidth());
	}

	@Test
	public void testGetHeight() {

		assertEquals(40, extent.getHeight());
	}

	@Test
	public void testEquals() {

		AjaxSeleniumTestSegment other = new AjaxSeleniumTestSegment(30, 40);
		assertTrue(extent.equals(other));
	}

	@Test
	public void testEqualsWithDifferentWidth() {

		AjaxSeleniumTestSegment other = new AjaxSeleniumTestSegment(99, 40);
		assertFalse(extent.equals(other));
	}

	@Test
	public void testEqualsWithDifferentHeight() {

		AjaxSeleniumTestSegment other = new AjaxSeleniumTestSegment(30, 99);
		assertFalse(extent.equals(other));
	}
}
