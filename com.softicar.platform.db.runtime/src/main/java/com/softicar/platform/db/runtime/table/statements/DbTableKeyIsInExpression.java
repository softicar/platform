package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression1;
import com.softicar.platform.db.sql.operations.SqlImploder;
import com.softicar.platform.db.sql.operations.SqlTuple;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class DbTableKeyIsInExpression<R extends IDbTableRow<R, P>, P> implements ISqlBooleanExpression1<R> {

	private final IDbTableKey<R, P> key;
	private final Set<P> keyValues;

	public DbTableKeyIsInExpression(IDbTable<R, P> table) {

		this.key = table.getPrimaryKey();
		this.keyValues = new TreeSet<>(table.getPrimaryKey());
	}

	public DbTableKeyIsInExpression<R, P> addKeysFromRows(Collection<? extends R> rows) {

		for (R row: rows) {
			addKeyValue(row.pk());
		}
		return this;
	}

	public DbTableKeyIsInExpression<R, P> addKeyValues(Collection<? extends P> keyValues) {

		this.keyValues.addAll(keyValues);
		return this;
	}

	public DbTableKeyIsInExpression<R, P> addKeyValue(P key) {

		this.keyValues.add(key);
		return this;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		if (keyValues.isEmpty()) {
			builder.addText("FALSE");
		} else if (DbConnections.getServerQuirks().isRowValueExpressionSupported()) {
			builder.addText("(");
			new SqlTuple().addAll(key.getFields()).build(builder);
			builder.addText(" IN ");
			SqlTuple list = new SqlTuple();
			for (P keyValue: keyValues) {
				list.add(new DbTableKeyValueSqlTuple<>(key, keyValue));
			}
			list.build(builder);
			builder.addText(")");
		} else {
			SqlImploder or = new SqlImploder(" OR ", "(", ")");
			for (P keyValue: keyValues) {
				SqlImploder and = new SqlImploder(" AND ", "(", ")");
				for (IDbField<R, ?> field: key.getFields()) {
					and.add(getFieldEqualExpression(field, keyValue));
				}
				or.add(and);
			}
			or.build(builder);
		}
	}

	private <V> ISqlBooleanExpression<R> getFieldEqualExpression(IDbField<R, V> field, P keyValue) {

		return field.isEqual(key.getValue(field, keyValue));
	}

	@Override
	public ISqlValueType<Boolean> getValueType() {

		return SqlValueTypes.BOOLEAN;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}
}
