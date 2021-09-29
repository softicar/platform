package com.softicar.platform.emf.log.viewer.item;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.log.viewer.EmfLogStructuralMapper;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class EmfLogItem<R extends IEmfTableRow<R, ?>> {

	private final IEmfTransactionObject<?> transactionObject;
	private final R impermanentTableRow;
	private final EmfLogStructuralMapper<R> mapper;
	private final Collection<IEmfAttribute<R, ?>> loggedAttributes;

	public EmfLogItem(IEmfTransactionObject<?> transactionObject, R impermanentTableRow, EmfLogStructuralMapper<R> mapper) {

		this.transactionObject = transactionObject;
		this.impermanentTableRow = impermanentTableRow;
		this.mapper = mapper;
		this.loggedAttributes = new HashSet<>();
	}

	public IEmfTransactionObject<?> getTransactionObject() {

		return transactionObject;
	}

	public R getImpermanentTableRow() {

		return impermanentTableRow;
	}

	public Collection<IEmfAttribute<R, ?>> getLoggedAttributes() {

		return loggedAttributes;
	}

	public <V> V getValue(IEmfAttribute<R, V> attribute) {

		return attribute.getValue(impermanentTableRow);
	}

	public <L, F extends IEmfTableRow<F, ?>> void setValues(EmfPlainChangeLogger<L, F, ?> logger, L logRecord) {

		logger//
			.getLoggedFields()
			.forEach(it -> setValue(logger, logRecord, it));
	}

	public <V> void setValue(IEmfAttribute<R, V> attribute, V value) {

		attribute.setValue(impermanentTableRow, value);
	}

	private <L, F extends IEmfTableRow<F, ?>, V> void setValue(EmfPlainChangeLogger<L, F, ?> logger, L logRecord, IDbField<F, V> entityField) {

		IDbField<L, V> logField = logger.getLogField(entityField).orElse(null);
		if (logField != null) {
			V logValue = logField.getValue(logRecord);
			Optional<IEmfAttribute<R, V>> attribute = mapper.getAttribute(entityField);
			if (attribute.isPresent()) {
				setValue(attribute.get(), logValue);
				loggedAttributes.add(attribute.get());
			}
		}
	}
}
