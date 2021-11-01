package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.Test;

@SuppressWarnings("resource")
public class DbTransactionsTest extends AbstractDbTestWithConnectionProfiler {

	private static final String A = "A";

	public DbTransactionsTest() {

		connection.execute(new DbStatement("CREATE TABLE T (name VARCHAR)"));
	}

	@Test
	public void testSupplier() {

		String value = DbTransactions.wrap(() -> {
			executeQuery("SELECT * FROM T");
			return A;
		}).get();

		assertEquals(A, value);
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "SELECT * FROM T");
		profiler.assertStatement(2, "COMMIT");
	}

	@Test
	public void testConsumer() {

		DbTransactions.wrap((String parameter) -> {
			execute("INSERT INTO T SET name = ?", parameter);
		}).accept(A);

		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "INSERT INTO T SET name = ?");
		profiler.assertStatement(2, "COMMIT");
	}

	@Test
	public void testFunction() {

		String value = DbTransactions.wrap((String parameter) -> {
			execute("INSERT INTO T SET name = ?", parameter);
			return parameter;
		}).apply(A);

		assertEquals(A, value);
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "INSERT INTO T SET name = ?");
		profiler.assertStatement(2, "COMMIT");
	}

	// ------------------------------ roll-back ------------------------------ //

	@Test
	public void testThrowingSupplier() {

		Supplier<String> supplier = DbTransactions.wrap(this::throwingSupplier);

		Asserts.assertThrows(RuntimeException.class, supplier::get);

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "ROLLBACK");
	}

	@Test
	public void testThrowingFunction() {

		Function<String, String> function = DbTransactions.wrap(this::throwingFunction);

		Asserts.assertThrows(RuntimeException.class, () -> function.apply(A));

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "ROLLBACK");

	}

	@Test
	public void testThrowingConsumer() {

		Consumer<String> consumer = DbTransactions.wrap(this::throwingConsumer);

		Asserts.assertThrows(RuntimeException.class, () -> consumer.accept(A));

		profiler.assertStatementCount(2);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatement(1, "ROLLBACK");
	}

	private String throwingSupplier() {

		throw new RuntimeException();
	}

	private String throwingFunction(String parameter) {

		DevNull.swallow(parameter);
		throw new RuntimeException();
	}

	private void throwingConsumer(String parameter) {

		DevNull.swallow(parameter);
		throw new RuntimeException();
	}
}
