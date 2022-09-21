package com.softicar.platform.db.core.statement;

import com.softicar.platform.common.core.utils.DevNull;

/**
 * A listener that is notified when an {@link IDbStatement} is executed.
 *
 * @author Oliver Richers
 */
public interface IDbStatementExecutionListener {

	/**
	 * Called before the execution of the given {@link DbStatement}.
	 *
	 * @param statement
	 *            the statement that will be executed (never null)
	 */
	default void beforeExecution(IDbStatement statement) {

		DevNull.swallow(statement);
	}

	/**
	 * Called after the execution of the given {@link DbStatement}.
	 *
	 * @param statement
	 *            the statement that was executed (never null)
	 */
	default void afterExecution(IDbStatement statement) {

		DevNull.swallow(statement);
	}
}
