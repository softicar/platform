package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import org.junit.Test;

public class DbLazyTransactionTest extends AbstractDbCoreTest {

	private final DbConnectionTestProfiler profiler;

	public DbLazyTransactionTest() {

		this.profiler = new DbConnectionTestProfiler();
		DbConnections.setProfiler(profiler);
	}

	@Test
	public void testWithCommitAndWithoutExecutingStatements() {

		try (DbLazyTransaction transaction = new DbLazyTransaction()) {
			transaction.commit();
		}
		profiler.assertStatements();
	}

	@Test
	public void testWithRollbackAndWithoutExecutingStatements() {

		try (DbLazyTransaction transaction = new DbLazyTransaction()) {
			// nothing to do
		}
		profiler.assertStatements();
	}

	@Test
	public void testWithExecutingSelectStatement() {

		try (DbLazyTransaction transaction = new DbLazyTransaction()) {
			profiler.assertStatements();

			new DbStatement().addText("SELECT 1").executeQuery().close();
			profiler.assertStatements("BEGIN", "SELECT 1");

			transaction.commit();
			profiler.assertStatements("BEGIN", "SELECT 1", "COMMIT");
		}
		profiler.assertStatements("BEGIN", "SELECT 1", "COMMIT");
	}

	@Test
	public void testWithDeepNesting() {

		try (DbLazyTransaction transaction1 = new DbLazyTransaction()) {
			try (DbLazyTransaction transaction2 = new DbLazyTransaction()) {
				try (DbLazyTransaction transaction3 = new DbLazyTransaction()) {
					profiler.assertStatements();

					new DbStatement().addText("SELECT 1").executeQuery().close();
					profiler.assertStatements("BEGIN", "SAVEPOINT sp1", "SAVEPOINT sp2", "SELECT 1");
				}
			}
		}
	}

	@Test
	public void testWithNestedNormalTransaction() {

		try (DbLazyTransaction lazyTransaction = new DbLazyTransaction()) {
			profiler.assertStatements();

			try (DbTransaction eagerTransaction = new DbTransaction()) {
				profiler.assertStatements("BEGIN", "SAVEPOINT sp1");

				eagerTransaction.commit();
				profiler.assertStatements("BEGIN", "SAVEPOINT sp1");
			}
			lazyTransaction.commit();
			profiler.assertStatements("BEGIN", "SAVEPOINT sp1", "COMMIT");
		}
		profiler.assertStatements("BEGIN", "SAVEPOINT sp1", "COMMIT");
	}
}
