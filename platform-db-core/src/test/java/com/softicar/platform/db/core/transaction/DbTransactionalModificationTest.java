package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.table.IDbCoreTableRow;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.Test;

@SuppressWarnings("resource")
public class DbTransactionalModificationTest extends AbstractDbTestWithConnectionProfiler {

	private static final int ID = 123;
	private static final int VALUE = 42;

	private final TestRowPredicate whenPredicate;
	private final TestRowPredicate whenNotPredicate;
	private boolean successfullReload;

	public DbTransactionalModificationTest() {

		this.whenPredicate = new TestRowPredicate("A", true);
		this.whenNotPredicate = new TestRowPredicate("B", false);
		this.successfullReload = true;

		execute("CREATE TABLE T (id INT, value INT)");
		execute("INSERT INTO T (id, value) VALUES (?, ?)", ID, VALUE);
	}

	@Test
	public void testApply() {

		Optional<TestRow> result = executeModification();

		assertTrue(result.isPresent());
		profiler.assertStatementCount(7);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SELECT * FROM T WHERE id = ?", ID);
		profiler.assertStatement(2, "SELECT * FROM T WHERE id = ? FOR UPDATE", ID);
		profiler.assertStatement(3, "SAVEPOINT A");
		profiler.assertStatement(4, "SAVEPOINT B");
		profiler.assertStatement(5, "UPDATE T SET value = ? WHERE id = ?", VALUE + 1, ID);
		profiler.assertStatement(6, "COMMIT");
	}

	@Test
	public void testApplyWithFailingReload() {

		this.successfullReload = false;

		Optional<TestRow> result = executeModification();

		assertFalse(result.isPresent());
		profiler.assertStatementCount(4);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SELECT * FROM T WHERE id = ?", ID);
		profiler.assertStatement(2, "SELECT * FROM T WHERE id = ? FOR UPDATE", ID);
		profiler.assertStatement(3, "ROLLBACK");
	}

	@Test
	public void testApplyWithFailingPredicate() {

		whenPredicate.setResult(false);

		Optional<TestRow> result = executeModification();

		assertFalse(result.isPresent());
		profiler.assertStatementCount(5);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SELECT * FROM T WHERE id = ?", ID);
		profiler.assertStatement(2, "SELECT * FROM T WHERE id = ? FOR UPDATE", ID);
		profiler.assertStatement(3, "SAVEPOINT A");
		profiler.assertStatement(4, "ROLLBACK");
	}

	@Test
	public void testApplyWithFailingLoad() {

		executeUpdate("DELETE FROM T WHERE id = ?", ID);

		Optional<TestRow> result = executeModification();

		assertFalse(result.isPresent());
		profiler.assertStatementCount(4);
		profiler.assertStatement(0, "DELETE FROM T WHERE id = ?", ID);
		profiler.assertStatement(1, "BEGIN");
		profiler.assertStatement(2, "SELECT * FROM T WHERE id = ?", ID);
		profiler.assertStatement(3, "ROLLBACK");
	}

	private Optional<TestRow> executeModification() {

		return DbTransactionalModification//
			.with(() -> loadRow(ID))
			.when(whenPredicate)
			.whenNot(whenNotPredicate)
			.apply(row -> row.incrementValue());
	}

	private TestRow loadRow(int id) {

		try (DbResultSet resultSet = executeQuery("SELECT * FROM T WHERE id = ?", id)) {
			return resultSet.next()? new TestRow(resultSet) : null;
		}
	}

	private class TestRow implements IDbCoreTableRow {

		private final int id;
		private int value;

		public TestRow(DbResultSet resultSet) {

			this.id = resultSet.getInt("id");
			this.value = resultSet.getInt("value");
		}

		@Override
		public boolean reloadForUpdate() {

			executeQuery("SELECT * FROM T WHERE id = ? FOR UPDATE", id);
			return successfullReload;
		}

		public TestRow incrementValue() {

			this.value = value + 1;
			executeUpdate("UPDATE T SET value = ? WHERE id = ?", value, id);
			return this;
		}
	}

	private class TestRowPredicate implements Predicate<TestRow> {

		private final String name;
		private boolean result;

		public TestRowPredicate(String name, boolean result) {

			this.name = name;
			this.result = result;
		}

		public void setResult(boolean result) {

			this.result = result;
		}

		@Override
		public boolean test(TestRow t) {

			// artificially creating SAVEPOINT to test execution order of predicates
			execute("SAVEPOINT " + name);
			return result;
		}
	}
}
