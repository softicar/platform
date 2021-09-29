package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.Supplier;

public abstract class AbstractSqlField<R, V> implements ISqlField<R, V> {

	private final Supplier<ISqlTable<R>> tableGetter;
	private final SqlFieldType fieldType;
	private final String sqlName;
	private final int index;

	public AbstractSqlField(Supplier<ISqlTable<R>> tableGetter, SqlFieldType fieldType, String sqlName, int index) {

		this.tableGetter = tableGetter;
		this.fieldType = fieldType;
		this.sqlName = sqlName;
		this.index = index;
	}

	// -------------------- buildable methods -------------------- //

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addField(this);
	}

	// -------------------- expression methods -------------------- //

	@Override
	public int getColumnCount() {

		return 1;
	}

	// -------------------- field methods -------------------- //

	@Override
	public ISqlTable<R> getTable() {

		return tableGetter.get();
	}

	@Override
	public int getIndex() {

		return index;
	}

	@Override
	public SqlFieldType getFieldType() {

		return fieldType;
	}

	@Override
	public ISqlValueType<V> getValueType() {

		return fieldType.getValueType();
	}

	@Override
	public String getName() {

		return sqlName;
	}

	@Override
	public final String toString() {

		return getName();
	}
}
