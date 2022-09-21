package com.softicar.platform.db.core;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.testing.AbstractTest;
import java.sql.Timestamp;
import org.junit.Test;

public class DbCastingTest extends AbstractTest {

	@Test
	public void testGetTimeWithNull() {

		assertNull(DbCasting.getTime(null));
	}

	@Test
	public void testGetTimeWithTime() {

		Time time = new Time(11, 22, 33);
		assertSame(time, DbCasting.getTime(time));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testGetTimeWithJavaSqlTime() {

		Time time = DbCasting.getTime(new java.sql.Time(11, 22, 33));
		assertEquals(11, time.getHour());
		assertEquals(22, time.getMinute());
		assertEquals(33, time.getSecond());
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testGetTimeWithTimestamp() {

		Timestamp timestamp = new Timestamp(2015, 11, 23, 17, 33, 55, 0);
		Time time = DbCasting.getTime(timestamp);
		assertEquals(17, time.getHour());
		assertEquals(33, time.getMinute());
		assertEquals(55, time.getSecond());
	}

	@Test
	public void testGetTimeWithString() {

		Time time = DbCasting.getTime("11:22:33");
		assertEquals(11, time.getHour());
		assertEquals(22, time.getMinute());
		assertEquals(33, time.getSecond());
	}
}
