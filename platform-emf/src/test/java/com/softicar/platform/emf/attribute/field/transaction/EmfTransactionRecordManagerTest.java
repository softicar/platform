package com.softicar.platform.emf.attribute.field.transaction;




import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.transaction.EmfTestTransaction;
import org.junit.Test;

public class EmfTransactionRecordManagerTest extends AbstractEmfTest {

	private final EmfTransactionRecordManager<EmfTestTransaction> manager;

	public EmfTransactionRecordManagerTest() {

		this.manager = new EmfTransactionRecordManager<>(EmfTestTransaction.TABLE);
	}

	@Test
	public void testGetOrInsertRecordWithSingleTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			EmfTestTransaction transactionRecord = manager.getOrInsertRecord();

			assertNotNull(transactionRecord);
			assertNotNull(transactionRecord.getId());
			assertEquals(transaction.getBegin(), transactionRecord.getAt());
			assertEquals(CurrentBasicUser.get(), transactionRecord.getBy());

			assertSame(transactionRecord, manager.getOrInsertRecord());
		}
	}

	@Test
	public void testGetOrInsertRecordWithCreationInNestedTransaction() {

		EmfTestTransaction transactionRecord = null;

		try (DbTransaction transactionA = new DbTransaction()) {
			try (DbTransaction transactionB = new DbTransaction()) {
				transactionRecord = manager.getOrInsertRecord();
				transactionB.commit();
			}
			assertSame(transactionRecord, manager.getOrInsertRecord());
		}
	}

	@Test
	public void testGetOrInsertRecordWithCreationInRootTransaction() {

		try (DbTransaction transactionA = new DbTransaction()) {
			EmfTestTransaction transactionRecord = manager.getOrInsertRecord();
			try (DbTransaction transactionB = new DbTransaction()) {
				assertSame(transactionRecord, manager.getOrInsertRecord());
			}
		}
	}
}
