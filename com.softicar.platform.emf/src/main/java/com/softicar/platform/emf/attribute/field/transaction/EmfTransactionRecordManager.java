package com.softicar.platform.emf.attribute.field.transaction;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Manager class for the insertion of transaction records.
 * <p>
 * This class uses {@link EmfTransactionRecordsHolder} to ensure that only one
 * transaction record is inserted per database root transaction.
 *
 * @author Oliver Richers
 */
public class EmfTransactionRecordManager<R extends IEmfTransactionObject<R>> {

	private final IDbTable<R, ?> recordTable;
	private final Supplier<R> recordFactory;
	private final Class<R> recordClass;

	public EmfTransactionRecordManager(IDbTable<R, ?> recordTable) {

		this.recordTable = recordTable;
		this.recordFactory = () -> createTransactionRecord();
		this.recordClass = recordTable.getValueClass();
	}

	/**
	 * Returns the transaction record.
	 * <p>
	 * If no record was created in the current {@link DbTransaction}, a new
	 * record will be inserted and returns. Otherwise, the existing record will
	 * be returned.
	 *
	 * @return the transaction record (never null)
	 */
	@SuppressWarnings("resource")
	public R getOrInsertRecord() {

		// Please note that we can be sure here, that a transaction is started,
		// and that DbTransaction.getCurrentTransaction() is not empty.
		Optional<IDbTransaction> transaction = DbConnections.getCurrentTransaction();

		Optional<R> record = findExistingRecord(transaction.get());
		if (record.isPresent()) {
			return record.get();
		} else {
			return transaction//
				.get()
				.getOrPutData(EmfTransactionRecordsHolder.class, EmfTransactionRecordsHolder::new)
				.getOrPutIfAbsent(recordClass, this::insertTransactionRecord);
		}
	}

	private R createTransactionRecord() {

		R record = recordTable.getRowFactory().get();
		record.setAt(DbConnections.getBeginOfRootTransaction());
		record.setByToCurrentUser();
		return record;
	}

	private R insertTransactionRecord() {

		R transactionRecord = recordFactory.get();
		recordTable.save(transactionRecord);
		return transactionRecord;
	}

	@SuppressWarnings("resource")
	private Optional<R> findExistingRecord(IDbTransaction transaction) {

		Optional<R> record = transaction//
			.getData(EmfTransactionRecordsHolder.class)
			.flatMap(holder -> holder.get(recordClass));
		if (record.isPresent()) {
			return record;
		} else {
			Optional<IDbTransaction> parentTransaction = transaction.getParentTransaction();
			return parentTransaction.isPresent()? findExistingRecord(parentTransaction.get()) : Optional.empty();
		}
	}
}
