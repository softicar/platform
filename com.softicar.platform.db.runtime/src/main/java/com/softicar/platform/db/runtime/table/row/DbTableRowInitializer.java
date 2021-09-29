package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;

public class DbTableRowInitializer<R extends AbstractDbTableRow<R, P>, P> implements IDbTableRowInitializer<R, P> {

	protected final IDbTable<R, P> table;
	protected final R row;

	public DbTableRowInitializer(R row) {

		this.table = row.table();
		this.row = row;
	}

	@Override
	public R initializeFlagsAndSetFieldsToNull() {

		initializeFlags();
		return row;
	}

	@Override
	public R initializeFlagsAndSetAllFieldsToDefault(DbTableRowFlag...flags) {

		initializeFlags(flags);
		initializeAllFieldsToDefault(table.getAllFields());
		return row;
	}

	@Override
	public R initializeFlagsAndSetPkFields(P primaryKey, DbTableRowFlag...flags) {

		initializeFlags(flags);
		initializePkFields(primaryKey);
		return row;
	}

	// ------------------------------ initialize flags ------------------------------ //

	protected void initializeFlags(DbTableRowFlag...flagsToEnable) {

		row.initializeFlags(flagsToEnable);
	}

	// ------------------------------ initialize fields ------------------------------ //

	protected void initializeAllFieldsToDefault(Collection<? extends IDbField<R, ?>> fields) {

		fields.forEach(field -> initializeFieldToDefault(field));
	}

	protected <V> void initializeFieldToDefault(IDbField<R, V> field) {

		field.setValueDirectly(row, field.getDefault());
	}

	protected void initializeDataFields(R source) {

		table.getDataFields().forEach(field -> initializeField(field, source));
	}

	protected <V> void initializeField(IDbField<R, V> field, R source) {

		V value = field.getValue(source);
		field.setValueDirectly(row, field.copyValue(value));
	}

	// ------------------------------ initialize primary key fields ------------------------------ //

	protected void initializePkFields(P primaryKey) {

		for (IDbField<R, ?> field: table.getPrimaryKey().getFields()) {
			initializePkField(field, primaryKey);
		}
	}

	protected <V> void initializePkField(IDbField<R, V> field, P primaryKey) {

		V value = table.getPrimaryKey().getValue(field, primaryKey);
		if (value == null) {
			throw new DbException("Illegal primary key value: The value for primary key field '%s' must not be null.", field);
		}
		field.setValueDirectly(row, value);
	}
}
