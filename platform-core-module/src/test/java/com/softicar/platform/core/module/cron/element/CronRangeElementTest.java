package com.softicar.platform.core.module.cron.element;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CronRangeElementTest extends AbstractTest {

	@Test
	public void testTest() {

		var cronElement = new CronRangeElement(1, 3);

		assertFalse(cronElement.test(0));
		assertTrue(cronElement.test(1));
		assertTrue(cronElement.test(2));
		assertTrue(cronElement.test(3));
		assertFalse(cronElement.test(4));
	}

	@Test
	public void testStepTest() {

		var cronElement = new CronRangeElement(1, 3, 2);

		assertFalse(cronElement.test(0));
		assertTrue(cronElement.test(1));
		assertFalse(cronElement.test(2));
		assertTrue(cronElement.test(3));
		assertFalse(cronElement.test(4));
	}
}
