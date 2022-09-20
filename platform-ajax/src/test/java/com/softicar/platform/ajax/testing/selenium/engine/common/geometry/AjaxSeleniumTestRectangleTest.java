package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AjaxSeleniumTestRectangleTest extends AbstractTest {

	private final AjaxSeleniumTestRectangle rectangle;

	public AjaxSeleniumTestRectangleTest() {

		this.rectangle = new AjaxSeleniumTestRectangle(10, 20, 30, 40);
	}

	@Test
	public void testGetX() {

		assertEquals(10, rectangle.getX());
	}

	@Test
	public void testGetY() {

		assertEquals(20, rectangle.getY());
	}

	@Test
	public void testGetWidth() {

		assertEquals(30, rectangle.getWidth());
	}

	@Test
	public void testGetHeight() {

		assertEquals(40, rectangle.getHeight());
	}

	@Test
	public void testGetLocation() {

		AjaxSeleniumTestPoint location = rectangle.getLocation();
		assertEquals(10, location.getX());
		assertEquals(20, location.getY());
	}

	@Test
	public void testGetSize() {

		AjaxSeleniumTestArea size = rectangle.getSize();
		assertEquals(30, size.getWidth());
		assertEquals(40, size.getHeight());
	}

	@Test
	public void testMoveBy() {

		AjaxSeleniumTestRectangle other = rectangle.moveBy(2, 3);
		assertNotSame(rectangle, other);
		assertEquals(12, other.getX());
		assertEquals(rectangle.getWidth(), other.getWidth());
		assertEquals(rectangle.getHeight(), other.getHeight());
	}

	@Test
	public void testMoveByWithNegativeValues() {

		AjaxSeleniumTestRectangle other = rectangle.moveBy(-2, -3);
		assertNotSame(rectangle, other);
		assertEquals(8, other.getX());
		assertEquals(17, other.getY());
		assertEquals(rectangle.getWidth(), other.getWidth());
		assertEquals(rectangle.getHeight(), other.getHeight());
	}

	@Test
	public void testMoveByWithZeroValues() {

		AjaxSeleniumTestRectangle other = rectangle.moveBy(0, 0);
		assertNotSame(rectangle, other);
		assertEquals(10, other.getX());
		assertEquals(20, other.getY());
		assertEquals(rectangle.getWidth(), other.getWidth());
		assertEquals(rectangle.getHeight(), other.getHeight());
	}

	@Test
	public void testEquals() {

		AjaxSeleniumTestRectangle other = new AjaxSeleniumTestRectangle(10, 20, 30, 40);
		assertTrue(rectangle.equals(other));
	}

	@Test
	public void testEqualsWithDifferentX() {

		AjaxSeleniumTestRectangle other = new AjaxSeleniumTestRectangle(99, 20, 30, 40);
		assertFalse(rectangle.equals(other));
	}

	@Test
	public void testEqualsWithDifferentY() {

		AjaxSeleniumTestRectangle other = new AjaxSeleniumTestRectangle(10, 99, 30, 40);
		assertFalse(rectangle.equals(other));
	}

	@Test
	public void testEqualsWithDifferentWidth() {

		AjaxSeleniumTestRectangle other = new AjaxSeleniumTestRectangle(10, 20, 99, 40);
		assertFalse(rectangle.equals(other));
	}

	@Test
	public void testEqualsWithDifferentHeight() {

		AjaxSeleniumTestRectangle other = new AjaxSeleniumTestRectangle(10, 20, 30, 99);
		assertFalse(rectangle.equals(other));
	}
}
