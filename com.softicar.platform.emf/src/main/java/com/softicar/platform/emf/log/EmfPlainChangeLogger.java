package com.softicar.platform.emf.log;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionRecordManager;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmfPlainChangeLogger<L, R extends IEmfTableRow<R, ?>, T extends IEmfTransactionObject<T>> implements IEmfChangeLogger<R> {

	private final IDbTable<L, ?> logTable;
	private final IDbForeignRowField<L, R, ?> tableRowField;
	private final IDbForeignField<L, T> transactionField;
	private final Map<IDbField<R, ?>, FieldMapping<?>> fieldMappings;

	public EmfPlainChangeLogger(IDbForeignRowField<L, R, ?> tableRowField, IDbForeignField<L, T> transactionField) {

		this.logTable = tableRowField.getTable();
		this.tableRowField = tableRowField;
		this.transactionField = transactionField;
		this.fieldMappings = new HashMap<>();
	}

	public IDbTable<L, ?> getLogTable() {

		return logTable;
	}

	public IDbForeignRowField<L, R, ?> getTableRowField() {

		return tableRowField;
	}

	public IDbForeignField<L, T> getTransactionField() {

		return transactionField;
	}

	public Collection<L> getLogRecords(R tableRow) {

		List<L> records = logTable//
			.createSelect()
			.where(tableRowField.isEqual(tableRow))
			.orderBy(transactionField)
			.list();
		transactionField.prefetch(records);
		return records;
	}

	public Collection<IDbField<R, ?>> getLoggedFields() {

		return fieldMappings.keySet();
	}

	public <V> Optional<IDbField<L, V>> getLogField(IDbField<R, V> field) {

		@SuppressWarnings("unchecked")
		FieldMapping<V> mapping = (FieldMapping<V>) fieldMappings.get(field);
		if (mapping != null) {
			return Optional.of(mapping.getLogField());
		} else {
			return Optional.empty();
		}
	}

	public <V> EmfPlainChangeLogger<L, R, T> addMapping(IDbField<R, V> field, IDbField<L, V> logField) {

		if (logField == tableRowField || logField == transactionField) {
			throw new SofticarDeveloperException("Redundant mapping of entity field '%s' in table '%s'.", field, field.getTable());
		}

		fieldMappings.put(field, new FieldMapping<>(field, logField));
		return this;
	}

	@Override
	public void logChange(R tableRow) {

		if (isChanged(tableRow)) {
			L logRecord = logTable.getRowFactory().get();
			tableRowField.setValue(logRecord, tableRow);
			transactionField.setValue(logRecord, getTransactionLog());
			for (FieldMapping<?> fieldMapping: fieldMappings.values()) {
				fieldMapping.applyValue(tableRow, logRecord);
			}
			logTable.save(logRecord);
		}
	}

	private boolean isChanged(R tableRow) {

		// check if at least one of the logged fields is changed
		Optional<EmfChangeTracker> changeTracker = DbConnections.getTransactionData(EmfChangeTracker.class);
		if (changeTracker.isPresent()) {
			for (IDbField<R, ?> field: fieldMappings.keySet()) {
				if (changeTracker.get().isChanged(tableRow, field)) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	private T getTransactionLog() {

		IDbTable<T, ?> transactionTable = transactionField.getTargetTable();
		return new EmfTransactionRecordManager<>(transactionTable).getOrInsertRecord();
	}

	private class FieldMapping<V> {

		private final IDbField<R, V> field;
		private final IDbField<L, V> logField;

		public FieldMapping(IDbField<R, V> field, IDbField<L, V> logField) {

			this.field = field;
			this.logField = logField;
		}

		public IDbField<L, V> getLogField() {

			return logField;
		}

		public void applyValue(R tableRow, L logRecord) {

			V value = field.getValue(tableRow);
			logField.setValue(logRecord, value);
		}
	}
}
