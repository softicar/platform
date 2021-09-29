package com.softicar.platform.db.sql.test;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.field.ISqlValueField;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.fields.ISqlStringField;
import com.softicar.platform.db.sql.fields.SqlForeignRowTestField;
import com.softicar.platform.db.sql.fields.SqlIdTestField;
import com.softicar.platform.db.sql.fields.SqlIntegerTestField;
import com.softicar.platform.db.sql.fields.SqlStringTestField;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import java.util.ArrayList;
import java.util.List;

public class SqlTestTable<R extends IBasicItem> implements ISqlTable<R> {

	private final DbTableName tableName;
	private final Class<R> rowClass;
	private final List<ISqlField<R, ?>> fields;

	public SqlTestTable(String databaseName, String tableName, Class<R> rowClass) {

		this.tableName = new DbTableName(databaseName, tableName);
		this.rowClass = rowClass;
		this.fields = new ArrayList<>();
	}

	public SqlIdTestField<R> addIdField(String name) {

		SqlIdTestField<R> field = new SqlIdTestField<>(this, name, fields.size());
		fields.add(field);
		return field;
	}

	public ISqlIntegerField<R> addIntegerField(String name) {

		SqlIntegerTestField<R> field = new SqlIntegerTestField<>(this, name, fields.size());
		fields.add(field);
		return field;
	}

	public ISqlStringField<R> addStringField(String name) {

		SqlStringTestField<R> field = new SqlStringTestField<>(this, name, fields.size());
		fields.add(field);
		return field;
	}

	public <F extends IBasicItem> ISqlForeignRowField<R, F, Integer> addForeignField(ISqlValueField<F, Integer> targetIdField, String name) {

		ISqlForeignRowField<R, F, Integer> field = new SqlForeignRowTestField<>(this, targetIdField, name, fields.size());
		fields.add(field);
		return field;
	}

	@Override
	public R getValue(DbResultSet resultSet, int index) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Class<R> getValueClass() {

		return rowClass;
	}

	@Override
	public int compare(R left, R right) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnCount() {

		throw new UnsupportedOperationException();
	}

	@Override
	public ISqlBooleanExpression<R> wrapBool(ISqlExpression<Boolean> expression) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ISqlValueType<R> getValueType() {

		return this;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		throw new UnsupportedOperationException();
	}

	@Override
	public DbTableName getFullName() {

		return tableName;
	}

	@Override
	public List<? extends ISqlField<R, ?>> getAllFields() {

		return fields;
	}

	@Override
	public R getStub(DbResultSet resultSet, int index) {

		throw new UnsupportedOperationException();
	}
}
