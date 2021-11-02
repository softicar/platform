package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DbDayFieldTest extends Assert {

	private final Day then;

	public DbDayFieldTest() {

		this.then = Day.fromYMD(2017, 1, 1);

		CurrentClock.set(new TestClock().setInstant(then.toDate().toInstant()));
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testHasDefaultWithDefaultToday() {

		assertTrue(TestObject.DAY_FIELD.isDefaultToday());
		assertTrue(TestObject.DAY_FIELD.hasDefault());
	}

	@Test
	public void testGetDefaultWithDefaultToday() {

		Day day = new TestObject().getDay();

		assertEquals("Day not correctly initialized.", then, day);
	}

	@SuppressWarnings("unused")
	private static class TestObject extends AbstractDbObject<TestObject> {

		// @formatter:off
		public static final DbObjectTableBuilder<TestObject, TestObject> BUILDER = new DbObjectTableBuilder<>("database", "table", TestObject::new, TestObject.class);
		public static final IDbIdField<TestObject> ID_FIELD = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final IDbDayField<TestObject> DAY_FIELD = BUILDER.addDayField("day", o -> o.day, (o, v) -> o.day = v).setDefaultToday();
		public static final DbObjectTable<TestObject> TABLE = new DbObjectTable<>(BUILDER);
		// @formatter:on

		private Integer id;
		private Day day;

		@Override
		public IDbObjectTable<TestObject> table() {

			return TABLE;
		}

		public Day getDay() {

			return DAY_FIELD.getValue(this);
		}
	}
}
