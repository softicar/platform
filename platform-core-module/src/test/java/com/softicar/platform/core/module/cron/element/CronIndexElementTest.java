package com.softicar.platform.core.module.cron.element;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CronIndexElementTest extends AbstractTest {

	@Test
	public void testTest() {

		var cronElement = new CronIndexElement(2);

		assertFalse(cronElement.test(0));
		assertFalse(cronElement.test(1));
		assertTrue(cronElement.test(2));
		assertFalse(cronElement.test(3));
	}
}
