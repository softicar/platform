package com.softicar.platform.db.core.statement;

import com.softicar.platform.db.core.table.IDbCoreTable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;

public class DbTableAccessListener extends DbStatementExecutionCollector {

	public void assertAccess(IDbCoreTable...tables) {

		assertAccess(new HashSet<>(Arrays.asList(tables)));
	}

	public void assertAccess(Set<IDbCoreTable> tables) {

		Assert.assertEquals(1, getStatements().size());

		IDbStatement statement = getStatements().get(0);
		Assert.assertEquals(tables, statement.getTables());
	}
}
