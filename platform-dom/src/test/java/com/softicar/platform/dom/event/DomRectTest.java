package com.softicar.platform.dom.event;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class DomRectTest extends Asserts {

	private final DomRect rect1;
	private final DomRect rect2;

	public DomRectTest() {

		this.rect1 = new DomRect(1.2, 2.1, 34.2, 213.2);
		this.rect2 = new DomRect(3.3, 5.2, 65.1, 33.5);
	}

	@Test
	public void testGetter() {

		assertEquals(1.2, rect1.getX(), 0.001);
		assertEquals(2.1, rect1.getY(), 0.001);
		assertEquals(34.2, rect1.getWidth(), 0.001);
		assertEquals(213.2, rect1.getHeight(), 0.001);
	}

	@Test
	public void testEquals() {

		assertEquals(rect1, rect1);
		assertEquals(rect2, rect2);
		assertNotEquals(rect1, rect2);
	}
}
