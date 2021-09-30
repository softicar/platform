package com.softicar.platform.db.runtime.table.row.getter;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.IDbConnectionProfiler;
import com.softicar.platform.db.core.statement.IDbStatement;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeSet;
import org.junit.Assert;

public class AbstractDbTableRowGetterTest extends AbstractDbTest {

	protected static final Integer A = 31;
	protected static final Integer B = 32;
	protected static final Integer C = 33;
	protected static final Integer D = 34;
	protected int statementExecutionCount;

	public AbstractDbTableRowGetterTest() {

		DbTestObject.TABLE//
			.createInsert()
			.set(DbTestObject.ID_FIELD, A)
			.nextRow()
			.set(DbTestObject.ID_FIELD, B)
			.nextRow()
			.set(DbTestObject.ID_FIELD, C)
			.nextRow()
			.set(DbTestObject.ID_FIELD, D)
			.executeMultiInsert();

		this.statementExecutionCount = 0;
		DbConnections.setProfiler(new Profiler());
	}

	protected void assertExecutedStatements(int expectedCount) {

		assertEquals(expectedCount, statementExecutionCount);
	}

	protected void assertMap(Map<Integer, DbTestObject> actualMap, Integer...expectedKeys) {

		assertEquals("wrong size of result map", expectedKeys.length, actualMap.size());
		assertEquals("wrong IDs in result map", new TreeSet<>(Arrays.asList(expectedKeys)), actualMap.keySet());
		actualMap.values().forEach(Assert::assertNotNull);
	}

	private class Profiler implements IDbConnectionProfiler {

		@Override
		public void acceptStatementStarted(IDbStatement statement) {

			// ignored
		}

		@Override
		public void acceptStatementFinished(IDbStatement statement) {

			statementExecutionCount++;
		}
	}
}
