package com.softicar.platform.db.core.transaction;

import java.util.Optional;

class DbTransactionInheritedDataFetcher<T> {

	private final IDbTransaction startTransaction;
	private final Class<T> dataClass;

	public DbTransactionInheritedDataFetcher(IDbTransaction transaction, Class<T> dataClass) {

		this.startTransaction = transaction;
		this.dataClass = dataClass;
	}

	public Optional<T> getData() {

		var transaction = startTransaction;
		while (transaction != null) {
			Optional<T> data = transaction.getData(dataClass);
			if (data.isPresent()) {
				return data;
			}
			transaction = transaction.getParentTransaction().orElse(null);
		}
		return Optional.empty();
	}
}
