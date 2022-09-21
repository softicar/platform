package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.container.map.instance.ClassInstanceMap;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Base implementation of {@link IDbTransaction}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbTransaction implements IDbTransaction {

	private static final DbStatement BEGIN = new DbStatement("BEGIN");
	private static final DbStatement COMMIT = new DbStatement("COMMIT");
	private static final DbStatement ROLLBACK = new DbStatement("ROLLBACK");
	protected final IDbConnection connection;
	protected final IDbTransactionHierarchy hierarchy;
	protected final Optional<IDbTransaction> parentTransaction;
	protected final DayTime begin;
	protected final int id;
	private boolean started;
	private boolean starting;
	private boolean closed;
	private ClassInstanceMap<Object> dataMap;

	protected AbstractDbTransaction(IDbConnection connection) {

		this.connection = connection;
		this.hierarchy = connection.getTransactionHierarchy();
		this.parentTransaction = hierarchy.getCurrentTransaction();
		this.id = hierarchy.registerTransaction(this);
		this.begin = parentTransaction.map(it -> it.getBegin()).orElse(DayTime.now());
		this.started = false;
		this.starting = false;
		this.closed = false;
		this.dataMap = null;
	}

	// -------------------- simple getters -------------------- //

	@Override
	public int getId() {

		return id;
	}

	@Override
	public boolean isCurrentTransaction() {

		return hierarchy//
			.getCurrentTransaction()
			.map(transaction -> transaction == this)
			.orElse(false);
	}

	@Override
	public Optional<IDbTransaction> getParentTransaction() {

		return parentTransaction;
	}

	@Override
	public DayTime getBegin() {

		return begin;
	}

	// -------------------- start, commit and rollback -------------------- //

	@Override
	public void start() {

		if (!started && !starting) {
			try {
				this.starting = true;
				startTransaction();
				this.started = true;
			} finally {
				this.starting = false;
			}
		}
	}

	/**
	 * Commits the changes of this transaction.
	 * <p>
	 * If this transaction is nested within another transaction this will do
	 * nothing. The changes will only be applied when the root transaction is
	 * committed.
	 */
	@Override
	public void commit() {

		if (closed) {
			throw new SofticarDeveloperException("Tried to commit closed transaction #%s.", id);
		} else if (!isCurrentTransaction()) {
			throw new SofticarDeveloperException("A nested transaction was not closed before transaction #%s.", id);
		}

		notifyDataObjects(data -> data.beforeCommit(this));
		if (started) {
			commitTransaction();
		}
		notifyDataObjects(data -> data.afterCommit(this));

		hierarchy.unregisterTransaction(this);
		closed = true;
	}

	/**
	 * Cancels the changes of this transaction.
	 */
	@Override
	public void rollback() {

		if (closed) {
			throw new SofticarDeveloperException("Tried to rollback closed transaction #%s.", id);
		}

		hierarchy//
			.getChildTransaction(this)
			.ifPresent(IDbTransaction::rollback);

		notifyDataObjects(data -> data.beforeRollback(this));
		if (started) {
			rollbackTransaction();
		}
		notifyDataObjects(data -> data.afterRollback(this));

		hierarchy.unregisterTransaction(this);
		closed = true;
	}

	@Override
	public void close() {

		if (!closed) {
			rollback();
		}
	}

	public boolean isClosed() {

		return closed;
	}

	// -------------------- data -------------------- //

	@Override
	public <T> void putData(T data) {

		if (dataMap == null) {
			dataMap = new ClassInstanceMap<>();
		}
		dataMap.putInstance(data);
	}

	@Override
	public <T> Optional<T> getData(Class<T> dataClass) {

		return dataMap != null? dataMap.getOptional(dataClass) : Optional.empty();
	}

	@Override
	public <T> T getOrPutData(Class<T> dataClass, Supplier<T> dataFactory) {

		if (dataMap == null) {
			dataMap = new ClassInstanceMap<>();
		}
		return dataMap.getOrPutInstance(dataClass, dataFactory);
	}

	private void notifyDataObjects(Consumer<IDbTransactionListener> callback) {

		if (dataMap != null) {
			// Please note that we need to extract all table listeners
			// into a separate list, because we could otherwise get a
			// concurrent modification exception on the data map if one
			// of the table listeners modifies the data map.
			List<IDbTransactionListener> listeners = dataMap//
				.getAll()
				.stream()
				.filter(IDbTransactionListener.class::isInstance)
				.map(IDbTransactionListener.class::cast)
				.collect(Collectors.toList());

			// notify table listeners
			listeners.forEach(callback);
		}
	}

	// -------------------- protected -------------------- //

	protected void startTransaction() {

		if (isRootTransaction()) {
			connection.execute(BEGIN);
		} else {
			connection.executeUncached(new DbStatement("SAVEPOINT sp" + id));
		}
	}

	protected void commitTransaction() {

		if (isRootTransaction()) {
			connection.execute(COMMIT);
		}
	}

	protected void rollbackTransaction() {

		if (isRootTransaction()) {
			connection.execute(ROLLBACK);
		} else {
			connection.executeUncached(new DbStatement("ROLLBACK TO SAVEPOINT sp" + id));
		}
	}
}
