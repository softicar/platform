package com.softicar.platform.emf.log;




import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.EmfTestSubObjectLog;
import com.softicar.platform.emf.transaction.EmfTestTransaction;
import java.util.List;
import org.junit.Test;

public class EmfPlainChangeLoggerTest extends AbstractEmfTest {

	private final EmfTestSubObject subObject;

	public EmfPlainChangeLoggerTest() {

		subObject = new EmfTestSubObject();
	}

	@Test
	public void testSaveWithTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			subObject.setName("foo");
			subObject.setValue(1337);
			subObject.setNotNullableValue(420);
			subObject.save();

			transaction.commit();
		}

		assertLog("foo", 1337, 420);
	}

	@Test
	public void testSaveWithoutTransaction() {

		subObject.setName("foo");
		subObject.setValue(77);
		subObject.setNotNullableValue(420);
		subObject.save();

		assertLog("foo", 77, 420);
	}

	@Test
	public void testMultipleSavesWithSameTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			subObject.setName("foo");
			subObject.setValue(333);
			subObject.setNotNullableValue(420);
			subObject.save();

			subObject.setName("bar");
			subObject.save();

			transaction.commit();
		}

		assertLog("bar", 333, 420);
	}

	@Test
	public void testMultipleSavesWithoutTransaction() {

		subObject.setName("foo");
		subObject.setValue(33);
		subObject.setNotNullableValue(420);
		subObject.save();

		subObject.setName("bar");
		subObject.setValue(44);
		subObject.setNotNullableValue(421);
		subObject.save();

		List<EmfTestTransaction> transactions = assertCount(2, EmfTestTransaction.TABLE.loadAll());
		assertLog(transactions.get(0), "foo", 33, 420);
		assertLog(transactions.get(1), "bar", 44, 421);
	}

	@Test
	public void testSaveWithNestedTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			try (DbTransaction nestedTransaction = new DbTransaction()) {
				subObject.setName("foo");
				subObject.setValue(42);
				subObject.setNotNullableValue(420);
				subObject.save();
				nestedTransaction.commit();
			}
			transaction.commit();
		}

		assertLog("foo", 42, 420);
	}

	@Test
	public void testSaveWithoutId() {

		subObject.setName("foo").setNotNullableValue(420).save();

		assertLog("foo", null, 420);
	}

	private void assertLog(String name, Integer value, Integer notNullableValue) {

		assertLog(assertTransaction(), name, value, notNullableValue);
	}

	private void assertLog(EmfTestTransaction transaction, String expectedName, Integer expectedValue, Integer expectedNotNullableValue) {

		EmfTestSubObjectLog log = Asserts
			.assertOne(
				EmfTestSubObjectLog.TABLE//
					.createSelect()
					.where(EmfTestSubObjectLog.TRANSACTION.isEqual(transaction)));
		assertSame(subObject, log.getSubObject());
		assertSame(transaction, log.getTransaction());
		assertEquals(expectedName, log.getName());
		assertEquals(expectedValue, log.getValue());
		assertEquals(expectedNotNullableValue, log.getNotNullableValue());
	}

	private EmfTestTransaction assertTransaction() {

		EmfTestTransaction transaction = assertOne(EmfTestTransaction.TABLE.loadAll());
		assertNotNull(transaction.getAt());
		assertSame(CurrentBasicUser.get(), transaction.getBy());
		return transaction;
	}
}
