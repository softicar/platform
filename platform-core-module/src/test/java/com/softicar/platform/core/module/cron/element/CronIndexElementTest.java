package com.softicar.platform.core.module.cron.element;

import org.junit.Assert;
import org.junit.Test;

public class CronIndexElementTest extends Assert {

	@Test
	public void testTest() {

		var cronElement = new CronIndexElement(2);

		assertFalse(cronElement.test(0));
		assertFalse(cronElement.test(1));
		assertTrue(cronElement.test(2));
		assertFalse(cronElement.test(3));
	}
}
