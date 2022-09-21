package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.Arrays;
import org.junit.Test;

public class DbTransactionTest extends AbstractDbCoreTest {

	private static final String A = "A";
	private static final String B = "B";
	private final DbConnectionTestProfiler profiler;
	private final TestListener listener;

	public DbTransactionTest() {

		this.profiler = new DbConnectionTestProfiler();
		this.listener = new TestListener();
		DbConnections.setProfiler(profiler);
	}

	// -------------------- commit -------------------- //

	@Test
	public void testCommitOnRootTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.putData(listener);
			transaction.commit();
		}

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "COMMIT");
		assertListenerNotifications(listener, "beforeCommit(0)", "afterCommit(0)");
	}

	@Test
	public void testCommitOnRootAndChildTransaction() {

		try (DbTransaction rootTransaction = new DbTransaction()) {
			try (DbTransaction childTransaction = new DbTransaction()) {
				childTransaction.putData(listener);
				childTransaction.commit();
			}
			rootTransaction.putData(listener);
			rootTransaction.commit();
		}

		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SAVEPOINT sp1");
		profiler.assertStatement(2, "COMMIT");
		assertListenerNotifications(//
			listener,
			"beforeCommit(1)",
			"afterCommit(1)",
			"beforeCommit(0)",
			"afterCommit(0)");
	}

	// -------------------- rollback -------------------- //

	@Test
	public void testRollbackOnRootTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.putData(listener);
			transaction.rollback();
		}

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "ROLLBACK");
		assertListenerNotifications(listener, "beforeRollback(0)", "afterRollback(0)");
	}

	@Test
	public void testRollbackOnChildTransaction() {

		try (DbTransaction rootTransaction = new DbTransaction()) {
			try (DbTransaction childTransaction = new DbTransaction()) {
				childTransaction.putData(listener);
				childTransaction.rollback();
			}
			rootTransaction.putData(listener);
			rootTransaction.commit();
		}

		profiler.assertStatementCount(4);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SAVEPOINT sp1");
		profiler.assertStatement(2, "ROLLBACK TO SAVEPOINT sp1");
		profiler.assertStatement(3, "COMMIT");
		assertListenerNotifications(//
			listener,
			"beforeRollback(1)",
			"afterRollback(1)",
			"beforeCommit(0)",
			"afterCommit(0)");
	}

	@Test
	@SuppressWarnings("resource")
	public void testRollbackOnRootTransactionWithOpenChildTransactions() {

		try (DbTransaction rootTransaction = new DbTransaction()) {
			new DbTransaction().putData(listener);
			new DbTransaction().putData(listener);
			rootTransaction.putData(listener);
			rootTransaction.rollback();
		}

		profiler.assertStatementCount(6);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SAVEPOINT sp1");
		profiler.assertStatement(2, "SAVEPOINT sp2");
		profiler.assertStatement(3, "ROLLBACK TO SAVEPOINT sp2");
		profiler.assertStatement(4, "ROLLBACK TO SAVEPOINT sp1");
		profiler.assertStatement(5, "ROLLBACK");
		assertListenerNotifications(//
			listener,
			"beforeRollback(2)",
			"afterRollback(2)",
			"beforeRollback(1)",
			"afterRollback(1)",
			"beforeRollback(0)",
			"afterRollback(0)");
	}

	// -------------------- close -------------------- //

	@Test
	public void testCloseWithoutCommitAndRollback() {

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.putData(listener);
			// skipping commit and rollback
		}

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "ROLLBACK");
		assertListenerNotifications(//
			listener,
			"beforeRollback(0)",
			"afterRollback(0)");
	}

	// -------------------- data -------------------- //

	@Test
	public void testSetData() {

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.putData(A);
			transaction.putData(B);
			assertEquals(B, transaction.getData(String.class).get());
		}
	}

	@Test
	public void testGetData() {

		try (DbTransaction transaction = new DbTransaction()) {
			assertFalse(transaction.getData(String.class).isPresent());
		}
	}

	@Test
	public void testGetOrPutData() {

		try (DbTransaction transaction = new DbTransaction()) {
			String data = transaction.getOrPutData(String.class, () -> A);
			assertEquals(A, data);
			assertEquals(A, transaction.getData(String.class).get());
		}
	}

	@Test
	public void testGetOrPutDataWithExistingEntry() {

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.putData(A);
			String data = transaction.getOrPutData(String.class, () -> B);
			assertEquals(A, data);
			assertEquals(A, transaction.getData(String.class).get());
		}
	}

	// -------------------- private -------------------- //

	private void assertListenerNotifications(TestListener listener, String...notifications) {

		assertEquals(Arrays.asList(notifications), listener.getNotifications());
	}
}
