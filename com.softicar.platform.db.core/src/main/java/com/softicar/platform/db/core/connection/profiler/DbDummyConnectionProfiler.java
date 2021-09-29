package com.softicar.platform.db.core.connection.profiler;

import com.softicar.platform.db.core.statement.IDbStatement;

public class DbDummyConnectionProfiler implements IDbConnectionProfiler {

	@Override
	public void acceptStatementStarted(IDbStatement statement) {

		// nothing to do by default
	}

	@Override
	public void acceptStatementFinished(IDbStatement statement) {

		// nothing to do by default
	}
}
