package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.runtime.transients.DbTransientTestField;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import java.util.Arrays;
import org.junit.Test;

/**
 * Some test-cases for {@link DbTableRowLoader}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbTableRowLoaderTest extends AbstractDbTest {

	public DbTableRowLoaderTest() {

		// create table and alter integer field type to VARCHAR to allow sabotage
		new DbStatement()//
			.addText("ALTER TABLE %s ALTER COLUMN `%s` VARCHAR", DbTestObject.TABLE, DbTestObject.INTEGER_FIELD)
			.addTable(DbTestObject.TABLE)
			.execute();
	}

	// -------------------------------- Flags -------------------------------- //

	@Test
	public void testStubFlagAfterLoadAsMapWithExceptionDuringResultSetReading() {

		// insert regular object
		int id1 = executeInsert("13");
		DbTestObject regularObject = DbTestObject.TABLE.get(id1);
		assertFalse(regularObject.stub());

		// insert another object and sabotage the field value to trigger an exception when reading the result set
		int id2 = executeInsert("abc");
		DbTestObject stubObject = DbTestObject.TABLE.getStub(id2);
		assertTrue(stubObject.stub());

		try {
			createLoader()//
				.addRow(stubObject)
				.loadAsMap();
			fail("Expected exception did not occur.");
		} catch (NumberFormatException exception) {
			// ignored because Exception is expected
			DevNull.swallow(exception);
		}

		assertTrue(stubObject.stub());
		assertFalse(regularObject.stub());
	}

	@Test
	public void testImpermanentFlagAfterLoadAsMapWithExceptionDuringResultSetReading() {

		// insert first object that we will retain
		int id1 = executeInsert("13");
		DbTestObject object1 = DbTestObject.TABLE.get(id1);
		assertFalse(object1.impermanent());

		// insert second object that we will delete
		int id2 = executeInsert("17");
		DbTestObject object2 = DbTestObject.TABLE.get(id2);
		assertFalse(object2.impermanent());

		// sabotage the column type and cell value to trigger an exception when reading the result set
		executeUpdate(id1, "abc");
		executeDelete(id2);

		try {
			createLoader()//
				.addRow(object1)
				.addRow(object2)
				.loadAsMap();
			fail("Expected exception did not occur.");
		} catch (NumberFormatException exception) {
			// ignored because Exception is expected
			DevNull.swallow(exception);
		}

		// straightforward
		assertFalse(object1.impermanent());

		// flag should still be true because exception occurred
		assertFalse(object2.impermanent());
	}

	@Test
	public void testDeletedFlagAfterLoadAsMapWithDeletedTableRow() {

		// insert table row and load object regularly
		int id = executeInsert("13");
		DbTestObject object = DbTestObject.TABLE.get(id);
		assertFalse(object.impermanent());
		assertFalse(object.stub());

		// delete table row and reload object
		executeDelete(id);
		createLoader()//
			.addRow(object)
			.loadAsMap();

		assertTrue(object.impermanent());
		assertFalse(object.stub());
	}

	@Test
	public void testFlagRestorationAfterRollback() {

		DbTestObject object = DbTestObject.TABLE.getStub(executeInsert("13"));

		try (DbTransaction transaction = new DbTransaction()) {
			createLoader()//
				.addRow(object)
				.reload();
			transaction.rollback();
		}

		assertTrue(object.stub());
	}

	// -------------------------------- Transient Fields -------------------------------- //

	@Test
	public void testTransientFieldAfterReloading() {

		final DbTransientTestField field = new DbTransientTestField(o -> o.getInteger().toString());

		// read value of field initially
		int id = executeInsert("13");
		DbTestObject object = DbTestObject.TABLE.get(id);
		assertEquals("13", field.getValue(object));

		// change value in database and read again
		executeUpdate(id, "17");
		assertEquals("13", field.getValue(object));

		// reload object and read again
		createLoader()//
			.addRow(object)
			.loadAsMap();
		assertEquals("17", field.getValue(object));
	}

	// -------------------------------- Reloading -------------------------------- //

	@Test
	public void testLockingWithoutTransactionAndReload() {

		DbTestObject object = DbTestObject.TABLE.get(executeInsert("13"));

		boolean reloaded = createLoader()//
			.addRow(object)
			.reload();

		assertTrue(reloaded);
	}

	@Test(expected = DbException.class)
	public void testLockingWithoutTransactionAndReloadForUpdate() {

		DbTestObject object = DbTestObject.TABLE.get(executeInsert("13"));

		// Expected to throw an exception about a missing transaction.
		createLoader()//
			.addRow(object)
			.setLock(SqlSelectLock.FOR_UPDATE)
			.reload();
	}

	@Test(expected = DbException.class)
	public void testLockingWithoutTransactionAndReloadWithSharedLock() {

		DbTestObject object = DbTestObject.TABLE.get(executeInsert("13"));

		// Expected to throw an exception about a missing transaction.
		createLoader()//
			.addRow(object)
			.setLock(SqlSelectLock.IN_SHARE_MODE)
			.reload();
	}

	// -------------------------------- Private Methods -------------------------------- //

	private DbTableRowLoader<DbTestObject, Integer> createLoader() {

		return new DbTableRowLoader<>(DbTestObject.TABLE);
	}

	private int executeInsert(String testValue) {

		return new DbStatement()//
			.addText("INSERT INTO %s SET `%s` = ?", DbTestObject.TABLE, DbTestObject.INTEGER_FIELD)
			.addParameter(testValue)
			.executeInsert();
	}

	private void executeUpdate(int id, String testValue) {

		new DbStatement()//
			.addText("UPDATE %s SET `%s` = ? WHERE %s = ?", DbTestObject.TABLE, DbTestObject.INTEGER_FIELD, DbTestObject.ID_FIELD)
			.addParameters(Arrays.asList(testValue, id))
			.executeUpdate();
	}

	private void executeDelete(int id) {

		new DbStatement()//
			.addText("DELETE FROM %s WHERE %s = ?", DbTestObject.TABLE, DbTestObject.ID_FIELD)
			.addParameter(id)
			.executeUpdate();
	}
}
