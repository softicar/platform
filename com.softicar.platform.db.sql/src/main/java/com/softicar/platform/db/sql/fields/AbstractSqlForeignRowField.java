package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.function.Supplier;

/**
 * Represents a table field that refers to the primary key of another table.
 *
 * @author Oliver Richers
 */
public abstract class AbstractSqlForeignRowField<R, F, FP> extends AbstractSqlField<R, F> implements ISqlForeignRowField<R, F, FP> {

	private final ISqlField<F, FP> targetField;
	private final ISqlValueType<F> valueType;

	public AbstractSqlForeignRowField(Supplier<ISqlTable<R>> tableGetter, ISqlField<F, FP> targetField, String sqlName, int index) {

		super(tableGetter, targetField.getFieldType(), sqlName, index);

		this.targetField = targetField;
		this.valueType = SqlValueTypes.createForeign(targetField::getTable);
	}

	@Override
	public ISqlField<F, FP> getTargetField() {

		return targetField;
	}

	@Override
	public ISqlTable<F> getTargetTable() {

		return targetField.getTable();
	}

	@Override
	public ISqlValueType<F> getValueType() {

		return valueType;
	}
}
