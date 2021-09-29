package com.softicar.platform.emf.attribute.field.transaction;

import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransactionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfTransactionRecordsHolder implements IDbTransactionListener {

	private final Map<Class<?>, Object> map = new HashMap<>();

	public void addIfAbsent(Object transactionRecord) {

		map.putIfAbsent(transactionRecord.getClass(), transactionRecord);
	}

	public <T> T getOrPutIfAbsent(Class<T> recordClass, Supplier<T> recordFactory) {

		map.putIfAbsent(recordClass, recordFactory.get());
		return recordClass.cast(map.get(recordClass));
	}

	public <T> Optional<T> get(Class<T> recordClass) {

		Object record = map.get(recordClass);
		return record != null? Optional.of(recordClass.cast(record)) : Optional.empty();
	}

	@Override
	public void afterCommit(IDbTransaction transaction) {

		transaction//
			.getParentTransaction()
			.ifPresent(this::elevate);
	}

	private void elevate(IDbTransaction parentTransaction) {

		EmfTransactionRecordsHolder parentHolder = parentTransaction.getOrPutData(EmfTransactionRecordsHolder.class, EmfTransactionRecordsHolder::new);
		map.values().forEach(parentHolder::addIfAbsent);
	}
}
