package com.softicar.platform.db.core.connection.profiler;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.statement.IDbStatement;

@FunctionalInterface
public interface IDbConnectionProfiler {

	void acceptStatementStarted(IDbStatement statement);

	default void acceptStatementFinished(IDbStatement statement) {

		// ignored by default
		DevNull.swallow(statement);
	}

	default void afterConnectionClosed() {

		// ignored by default
	}
}
