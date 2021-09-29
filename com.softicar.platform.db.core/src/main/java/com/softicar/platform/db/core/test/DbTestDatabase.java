package com.softicar.platform.db.core.test;

import com.softicar.platform.common.core.threading.SynchronizedCounter;
import com.softicar.platform.db.core.dbms.h2.DbH2Database;
import com.softicar.platform.db.core.statement.IDbStatement;
import com.softicar.platform.db.core.statement.IDbStatementExecutionListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Extends {@link DbH2Database} and generates artificial database names.
 * <p>
 * Since this class may be used in a multi-threaded manner, we need to
 * synchronize all access to the listeners.
 *
 * @author Oliver Richers
 */
public class DbTestDatabase extends DbH2Database {

	private static SynchronizedCounter counter = new SynchronizedCounter();
	private final Collection<IDbStatementExecutionListener> listeners;

	public DbTestDatabase() {

		super("" + counter.increment());
		this.listeners = new ArrayList<>();
		setPermanent(true);
	}

	public synchronized void addListener(IDbStatementExecutionListener listener) {

		listeners.add(listener);
	}

	@Override
	public synchronized void beforeExecution(IDbStatement statement) {

		listeners.forEach(listener -> listener.beforeExecution(statement));
	}

	@Override
	public synchronized void afterExecution(IDbStatement statement) {

		listeners.forEach(listener -> listener.afterExecution(statement));
	}
}
