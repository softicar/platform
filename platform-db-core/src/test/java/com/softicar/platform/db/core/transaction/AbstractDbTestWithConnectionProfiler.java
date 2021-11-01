package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.Arrays;
import org.junit.Before;

public class AbstractDbTestWithConnectionProfiler extends AbstractDbCoreTest {

	protected final DbConnectionTestProfiler profiler;
	protected final IDbConnection connection;

	public AbstractDbTestWithConnectionProfiler() {

		this.profiler = new DbConnectionTestProfiler();
		this.connection = DbConnections.getConnection();

		connection.setProfiler(profiler);
	}

	@Before
	public void before() {

		profiler.clear();
	}

	protected void execute(String statement, Object...parameters) {

		connection.execute(new DbStatement(statement).addParameters(Arrays.asList(parameters)));
	}

	protected DbResultSet executeQuery(String query, Object...parameters) {

		return connection.executeQuery(new DbStatement(query).addParameters(Arrays.asList(parameters)));
	}

	protected int executeUpdate(String update, Object...parameters) {

		return connection.executeUpdate(new DbStatement(update).addParameters(Arrays.asList(parameters)));
	}
}
