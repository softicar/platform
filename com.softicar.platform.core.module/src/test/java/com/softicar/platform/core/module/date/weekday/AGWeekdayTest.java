package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.date.Weekday;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class AGWeekdayTest extends AbstractDbTest {

	private final AGWeekday monday;
	private final AGWeekday tuesday;
	private final AGWeekday wednesday;
	private final AGWeekday thursday;
	private final AGWeekday friday;
	private final AGWeekday saturday;
	private final AGWeekday sunday;

	public AGWeekdayTest() {

		this.monday = AGWeekdayEnum.MONDAY.getRecord();
		this.tuesday = AGWeekdayEnum.TUESDAY.getRecord();
		this.wednesday = AGWeekdayEnum.WEDNESDAY.getRecord();
		this.thursday = AGWeekdayEnum.THURSDAY.getRecord();
		this.friday = AGWeekdayEnum.FRIDAY.getRecord();
		this.saturday = AGWeekdayEnum.SATURDAY.getRecord();
		this.sunday = AGWeekdayEnum.SUNDAY.getRecord();
	}

	@Test
	public void testGetByWeekday() {

		assertSame(monday, AGWeekday.getByWeekday(Weekday.MONDAY));
		assertSame(tuesday, AGWeekday.getByWeekday(Weekday.TUESDAY));
		assertSame(wednesday, AGWeekday.getByWeekday(Weekday.WEDNESDAY));
		assertSame(thursday, AGWeekday.getByWeekday(Weekday.THURSDAY));
		assertSame(friday, AGWeekday.getByWeekday(Weekday.FRIDAY));
		assertSame(saturday, AGWeekday.getByWeekday(Weekday.SATURDAY));
		assertSame(sunday, AGWeekday.getByWeekday(Weekday.SUNDAY));
	}

	@Test
	public void testGetWeekday() {

		assertSame(Weekday.MONDAY, monday.getWeekday());
		assertSame(Weekday.TUESDAY, tuesday.getWeekday());
		assertSame(Weekday.WEDNESDAY, wednesday.getWeekday());
		assertSame(Weekday.THURSDAY, thursday.getWeekday());
		assertSame(Weekday.FRIDAY, friday.getWeekday());
		assertSame(Weekday.SATURDAY, saturday.getWeekday());
		assertSame(Weekday.SUNDAY, sunday.getWeekday());
	}
}
