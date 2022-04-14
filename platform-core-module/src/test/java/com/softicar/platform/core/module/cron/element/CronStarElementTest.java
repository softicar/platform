package com.softicar.platform.core.module.cron.element;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CronStarElementTest extends AbstractTest {

	@Test
	public void testTest() {

		var cronElement = new CronStarElement();

		assertTrue(cronElement.test(0));
		assertTrue(cronElement.test(1));
		assertTrue(cronElement.test(2));
		assertTrue(cronElement.test(3));
	}

	@Test
	public void testStepTest() {

		var cronElement = new CronStarElement(3);

		assertTrue(cronElement.test(0));
		assertFalse(cronElement.test(1));
		assertFalse(cronElement.test(2));
		assertTrue(cronElement.test(3));
	}
}
