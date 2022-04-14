package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link DoubleSchedule}.
 *
 * @author Oliver Richers
 */
public class DoubleScheduleTest extends AbstractTest {

	@Test
	public void testSettingBackoder() {

		DoubleSchedule<String, String> schedule = new DoubleSchedule<>("B");
		schedule.setBackorder("A2", 2.0);
		assertEquals(0.0, schedule.getBackorder("A1"), 0.1);
		assertEquals(2.0, schedule.getBackorder("A2"), 0.1);
	}

	@Test
	public void testAddingValues() {

		DoubleSchedule<String, String> schedule = new DoubleSchedule<>("B");
		schedule.setValue("A1", "Monday", 15.0);
		schedule.addValue("A1", "Monday", -4.5);
		assertEquals(10.5, schedule.getValue("A1", "Monday"), 0.1);
	}

	@Test
	public void testMakingBackorder() {

		DoubleSchedule<String, String> schedule = new DoubleSchedule<>("B");
		schedule.setValue("A1", "Monday", 3.2);
		schedule.setValue("A1", "TuesDay", 1.5);
		schedule.setValue("A1", "Wednesday", 2.3);
		schedule.makeBackorderBefore("Wednesday");
		assertEquals(4.7, schedule.getBackorder("A1"), 0.1);
		assertEquals(0.0, schedule.getValue("A1", "Monday"), 0.1);
		assertEquals(0.0, schedule.getValue("A1", "Tuesday"), 0.1);
		assertEquals(2.3, schedule.getValue("A1", "Wednesday"), 0.1);
		assertEquals(7.0, schedule.getTotalValue("A1"), 0.1);
	}
}
