package com.softicar.platform.emf.log;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.ArrayList;
import java.util.Collection;

public class EmfChangeLoggerSet<R extends IEmfTableRow<R, ?>> implements IEmfChangeLoggerSet<R> {

	private final Collection<IEmfChangeLogger<R>> loggers;

	public EmfChangeLoggerSet() {

		this.loggers = new ArrayList<>();
	}

	@Override
	public Collection<IEmfChangeLogger<R>> getLoggers() {

		return loggers;
	}

	public void addChangeLogger(IEmfChangeLogger<R> changeLogger) {

		this.loggers.add(changeLogger);
	}

	public EmfDummyLogger<R> addDummyLogger() {

		EmfDummyLogger<R> logger = new EmfDummyLogger<>();
		addChangeLogger(logger);
		return logger;
	}

	public <L, T extends IEmfTransactionObject<T>> EmfPlainChangeLogger<L, R, T> addPlainChangeLogger(IDbForeignRowField<L, R, ?> entityField,
			IDbForeignField<L, T> transactionField) {

		EmfPlainChangeLogger<L, R, T> logger = new EmfPlainChangeLogger<>(entityField, transactionField);
		addChangeLogger(logger);
		return logger;
	}
}
