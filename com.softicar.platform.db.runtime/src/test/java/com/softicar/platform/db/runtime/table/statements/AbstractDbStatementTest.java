package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.statement.IDbStatement;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AbstractDbStatementTest extends AbstractDbObjectTest {

	protected List<IDbStatement> executedStatements;

	public AbstractDbStatementTest() {

		this.executedStatements = new ArrayList<>();

		DbConnections.setProfiler(executedStatements::add);
	}

	protected DbTestObject createAndSaveObject(int integer, String string) {

		DbTestObject object = new DbTestObject();
		object.setInteger(integer);
		object.setString(string);
		object.save();
		return object;
	}

	protected void assertSqlStatement(String expectedStatement, Object...expectedParameters) {

		assertSqlStatement(expectedStatement, Arrays.asList(expectedParameters));
	}

	protected void assertSqlStatement(String expectedStatement, Collection<?> expectedParameters) {

		assertEquals("executed statements", 1, executedStatements.size());
		assertEquals(expectedStatement, executedStatements.get(0).getText());
		assertEquals(expectedParameters, executedStatements.get(0).getParameters());
	}
}
