package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AjaxSeleniumTestPointTest extends AbstractTest {

	private final AjaxSeleniumTestPoint point;

	public AjaxSeleniumTestPointTest() {

		this.point = new AjaxSeleniumTestPoint(10, 20);
	}

	@Test
	public void testGetX() {

		assertEquals(10, point.getX());
	}

	@Test
	public void testGetY() {

		assertEquals(20, point.getY());
	}

	@Test
	public void testMoveBy() {

		AjaxSeleniumTestPoint other = point.moveBy(2, 3);
		assertNotSame(point, other);
		assertEquals(12, other.getX());
		assertEquals(23, other.getY());
	}

	@Test
	public void testMoveByWithNegativeValues() {

		AjaxSeleniumTestPoint other = point.moveBy(-2, -3);
		assertNotSame(point, other);
		assertEquals(8, other.getX());
		assertEquals(17, other.getY());
	}

	@Test
	public void testMoveByWithZeroValues() {

		AjaxSeleniumTestPoint other = point.moveBy(0, 0);
		assertNotSame(point, other);
		assertEquals(10, other.getX());
		assertEquals(20, other.getY());
	}

	@Test
	public void testEquals() {

		AjaxSeleniumTestPoint other = new AjaxSeleniumTestPoint(10, 20);
		assertTrue(point.equals(other));
	}

	@Test
	public void testEqualsWithDifferentX() {

		AjaxSeleniumTestPoint other = new AjaxSeleniumTestPoint(99, 20);
		assertFalse(point.equals(other));
	}

	@Test
	public void testEqualsWithDifferentY() {

		AjaxSeleniumTestPoint other = new AjaxSeleniumTestPoint(10, 99);
		assertFalse(point.equals(other));
	}
}
