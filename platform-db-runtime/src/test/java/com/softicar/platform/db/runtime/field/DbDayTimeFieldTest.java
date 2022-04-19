package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import org.junit.After;
import org.junit.Test;

public class DbDayTimeFieldTest extends AbstractTest {

	private final DayTime then;

	public DbDayTimeFieldTest() {

		this.then = DayTime.fromYMD_HMS(2017, 1, 1, 13, 37, 11);

		CurrentClock.set(new TestClock().setInstant(then.toDate().toInstant()));
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testHasDefaultWithDefaultNow() {

		assertTrue(TestObject.DAY_TIME_FIELD.isDefaultNow());
		assertTrue(TestObject.DAY_TIME_FIELD.hasDefault());
	}

	@Test
	public void testGetDefaultWithDefaultNow() {

		DayTime dayTime = new TestObject().getDayTime();

		assertEquals("Day time not correctly initialized.", then, dayTime);
	}

	@SuppressWarnings("unused")
	private static class TestObject extends AbstractDbObject<TestObject> {

		// @formatter:off
		public static final DbObjectTableBuilder<TestObject, TestObject> BUILDER = new DbObjectTableBuilder<>("database", "table", TestObject::new, TestObject.class);
		public static final IDbIdField<TestObject> ID_FIELD = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final IDbDayTimeField<TestObject> DAY_TIME_FIELD = BUILDER.addDayTimeField("dayTime", o -> o.dayTime, (o, v) -> o.dayTime = v).setDefaultNow();
		public static final DbObjectTable<TestObject> TABLE = new DbObjectTable<>(BUILDER);
		// @formatter:on

		private Integer id;
		private DayTime dayTime;

		@Override
		public IDbObjectTable<TestObject> table() {

			return TABLE;
		}

		public DayTime getDayTime() {

			return DAY_TIME_FIELD.getValue(this);
		}
	}
}
