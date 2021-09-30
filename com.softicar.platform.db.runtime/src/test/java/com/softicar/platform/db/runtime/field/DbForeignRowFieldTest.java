package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;

public class DbForeignRowFieldTest extends AbstractDbTest {

	@Test
	public void testGetTable() {

		assertSame(TestRecordB.TABLE, TestRecordB.RECORD_A.getTable());
	}

	@Test
	public void testGetTargetField() {

		assertSame(TestRecordA.NAME, TestRecordB.RECORD_A.getTargetField());
	}

	@Test
	public void testGetTargetTable() {

		assertSame(TestRecordA.TABLE, TestRecordB.RECORD_A.getTargetTable());
	}

	@Test
	public void testGetFieldType() {

		assertEquals(SqlFieldType.STRING, TestRecordB.RECORD_A.getFieldType());
	}

	@Test
	public void testGetValueType() {

		ISqlValueType<TestRecordA> valueType = TestRecordB.RECORD_A.getValueType();

		assertEquals(1, valueType.getColumnCount());
		assertSame(TestRecordA.class, valueType.getValueClass());
	}

	@Test
	public void testPrefetch() {

		TestRecordA a = insertRecordA("a", 1);
		TestRecordA b = insertRecordA("b", 2);
		TestRecordA c = insertRecordA("c", 3);

		TestRecordB record6 = insertRecordB(6, null);
		TestRecordB record7 = insertRecordB(7, a);
		TestRecordB record8 = insertRecordB(8, b);
		TestRecordB record9 = insertRecordB(9, c);

		Collection<TestRecordA> records = TestRecordB.RECORD_A.prefetch(Arrays.asList(record6, record7, record8, record9));

		assertEquals(Arrays.asList(a, b, c), records);
		assertFalse(a.stub());
		assertFalse(b.stub());
		assertFalse(c.stub());
	}

	private TestRecordA insertRecordA(String name, int value) {

		TestRecordA.TABLE//
			.createInsert()
			.set(TestRecordA.NAME, name)
			.set(TestRecordA.VALUE, value)
			.executeWithoutIdGeneration();
		return TestRecordA.TABLE.getStub(name);
	}

	private TestRecordB insertRecordB(Integer id, TestRecordA recordA) {

		TestRecordB.TABLE//
			.createInsert()
			.set(TestRecordB.ID, id)
			.set(TestRecordB.RECORD_A, recordA)
			.executeWithoutIdGeneration();
		return TestRecordB.TABLE.getStub(id);
	}

	public static class TestRecordA extends AbstractDbRecord<TestRecordA, String> {

		// @formatter:off
		public static final DbTableBuilder<TestRecordA, TestRecordA, String> BUILDER = new DbTableBuilder<>("db", "a", TestRecordA::new, TestRecordA.class);
		public static final IDbStringField<TestRecordA> NAME = BUILDER.addStringField("name", r -> r.name, (r, v) -> r.name = v);
		public static final IDbIntegerField<TestRecordA> VALUE = BUILDER.addIntegerField("value", r -> r.value, (r, v) -> r.value = v);
		public static final IDbTableKey<TestRecordA, String> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(NAME));
		public static final DbRecordTable<TestRecordA, String> TABLE = new DbRecordTable<>(BUILDER);
		// @formatter:on

		private String name;
		private Integer value;

		@Override
		public IDbRecordTable<TestRecordA, String> table() {

			return TABLE;
		}
	}

	public static class TestRecordB extends AbstractDbRecord<TestRecordB, Integer> {

		// @formatter:off
		public static final DbTableBuilder<TestRecordB, TestRecordB, Integer> BUILDER = new DbTableBuilder<>("db", "b", TestRecordB::new, TestRecordB.class);
		public static final IDbIntegerField<TestRecordB> ID = BUILDER.addIntegerField("id", r -> r.id, (r, v) -> r.id = v);
		public static final IDbForeignRowField<TestRecordB, TestRecordA, String> RECORD_A = BUILDER.addForeignRowField("recordA", r -> r.recordA, (r, v) -> r.recordA = v, TestRecordA.NAME).setNullable();
		public static final IDbTableKey<TestRecordB, Integer> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ID));
		public static final DbRecordTable<TestRecordB, Integer> TABLE = new DbRecordTable<>(BUILDER);
		// @formatter:on

		private Integer id;
		private TestRecordA recordA;

		@Override
		public IDbRecordTable<TestRecordB, Integer> table() {

			return TABLE;
		}
	}
}
