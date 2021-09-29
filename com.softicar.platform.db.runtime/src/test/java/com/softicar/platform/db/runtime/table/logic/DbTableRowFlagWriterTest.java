package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.record.DbTestRecord;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableRowFlagWriterTest extends AbstractDbTest {

	private final DbTestObject object;
	private final DbTableRowFlagsAsserter flagsAsserter;

	public DbTableRowFlagWriterTest() {

		this.object = new DbTestObject().save();
		this.flagsAsserter = new DbTableRowFlagsAsserter(object);
	}

	@Test
	public void testSetImpermanent() {

		new DbTableRowFlagWriter(object).setImpermanent(true);

		flagsAsserter.assertOnlyImpermanent();
	}

	@Test
	public void testSetDataChanged() {

		new DbTableRowFlagWriter(object).setDataChanged(DbTestObject.STRING_FIELD);

		flagsAsserter.assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}

	@Test
	public void testSetDataChangedWithIdField() {

		new DbTableRowFlagWriter(object).setDataChanged(DbTestObject.ID_FIELD);

		flagsAsserter.assertNone();
	}

	@Test
	public void testSetDataChangedWithPkField() {

		DbTestRecord record = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("foo", 13)).save();

		new DbTableRowFlagWriter(record).setDataChanged(DbTestRecord.NAME);
		new DbTableRowFlagWriter(record).setDataChanged(DbTestRecord.INDEX);

		new DbTableRowFlagsAsserter(record).assertNone();
	}

	@Test
	public void testSetDataChangedWithMultipleFields() {

		new DbTableRowFlagWriter(object).setDataChanged(DbTestObject.STRING_FIELD);
		new DbTableRowFlagWriter(object).setDataChanged(DbTestObject.INTEGER_FIELD);

		flagsAsserter
			.assertOnlyDirtyAndDataChanged(//
				DbTestObject.STRING_FIELD,
				DbTestObject.INTEGER_FIELD);
	}

	@Test
	public void testSetDirty() {

		new DbTableRowFlagWriter(object).setDirty(true);

		flagsAsserter.assertOnlyDirty();
	}

	@Test
	public void testSetInvalidated() {

		new DbTableRowFlagWriter(object).setInvalidated(true);

		flagsAsserter.assertOnlyInvalidated();
	}

	@Test
	public void testSetStub() {

		new DbTableRowFlagWriter(object).setStub(true);

		flagsAsserter.assertOnlyStub();
	}

	@Test
	public void testMultipleFlags() {

		new DbTableRowFlagWriter(object).setDirty(true);
		new DbTableRowFlagWriter(object).setInvalidated(true);
		new DbTableRowFlagWriter(object).setStub(true);
		new DbTableRowFlagWriter(object).setInvalidated(false);

		flagsAsserter.assertOnly(DbTableRowFlag.DIRTY, DbTableRowFlag.STUB);
	}

	@Test
	public void testClearFlags() {

		new DbTableRowFlagWriter(object).setDirty(true);
		new DbTableRowFlagWriter(object).setInvalidated(true);
		new DbTableRowFlagWriter(object).setStub(true);
		new DbTableRowFlagWriter(object).clearFlags();

		flagsAsserter.assertNone();
	}
}
