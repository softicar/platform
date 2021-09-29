package com.softicar.platform.db.core.transaction;

import java.util.Collection;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class contains the logic to control nesting of {@link DbTransaction}.
 * <p>
 * One instances of this class is created for every database connection, so that
 * transactions on one database connection do not interfere with transaction of
 * another connection.
 *
 * @see DbTransaction
 * @author Oliver Richers
 */
public class DbTransactionHierarchy implements IDbTransactionHierarchy {

	private final SortedMap<Integer, IDbTransaction> transactions;
	private boolean allTransactionsStarted;
	private boolean startingLazyTransactions;
	private int transactionId;

	public DbTransactionHierarchy() {

		this.transactions = new TreeMap<>();
		this.allTransactionsStarted = false;
		this.startingLazyTransactions = false;
		this.transactionId = -1;
	}

	@Override
	@SuppressWarnings("resource")
	public Optional<IDbTransaction> getRootTransaction() {

		return Optional.ofNullable(transactions.isEmpty()? null : transactions.get(transactions.firstKey()));
	}

	@Override
	@SuppressWarnings("resource")
	public Optional<IDbTransaction> getCurrentTransaction() {

		return Optional.ofNullable(transactions.isEmpty()? null : transactions.get(transactions.lastKey()));
	}

	@Override
	@SuppressWarnings("resource")
	public Optional<IDbTransaction> getChildTransaction(IDbTransaction transaction) {

		Collection<IDbTransaction> children = transactions.tailMap(transaction.getId() + 1).values();
		if (children.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(children.iterator().next());
		}
	}

	@Override
	@SuppressWarnings("resource")
	public int registerTransaction(IDbTransaction transaction) {

		this.allTransactionsStarted = false;
		this.transactionId += 1;
		transactions.put(transactionId, transaction);
		return transactionId;
	}

	@Override
	@SuppressWarnings("resource")
	public void unregisterTransaction(IDbTransaction transaction) {

		transactions.remove(transaction.getId());
	}

	public void startLazyTransactions() {

		if (!allTransactionsStarted && !startingLazyTransactions) {
			try {
				this.startingLazyTransactions = true;
				transactions.values().forEach(IDbTransaction::start);
				this.allTransactionsStarted = true;
			} finally {
				this.startingLazyTransactions = false;
			}
		}
	}
}
