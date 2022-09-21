package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.utils.DbResultSetFieldReader;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DbTableKey1<R, P> extends AbstractDbTableKey<R, P> {

	protected final IDbField<R, P> field;

	public DbTableKey1(IDbField<R, P> field) {

		this.field = field;
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return Collections.singletonList(field);
	}

	@Override
	public boolean containsField(IDbField<R, ?> field) {

		return field == this.field;
	}

	@Override
	public boolean isGenerated() {

		return false;
	}

	@Override
	public boolean isBase() {

		return false;
	}

	@Override
	public IDbField<R, Integer> getIdField() {

		throw new UnsupportedOperationException("This key has no id-field.");
	}

	@Override
	public <V> V getValue(IDbField<R, V> field, P primaryKey) {

		Class<V> valueClass = field.getValueType().getValueClass();
		return valueClass.cast(primaryKey);
	}

	@Override
	public P getFromRow(R row) {

		return field.getValueDirectly(row);
	}

	@Override
	public int compare(P left, P right) {

		return field.getValueType().compare(left, right);
	}

	@Override
	public P getFromResultSet(DbResultSet resultSet, int baseIndex) {

		return new DbResultSetFieldReader(resultSet, baseIndex).read(field);
	}

	@Override
	public ISqlBooleanExpression<R> isEqual(P keyValue) {

		return field.isEqual(keyValue);
	}

	@Override
	public ISqlBooleanExpression<R> isIn(Collection<P> keyValues) {

		return field.isIn(keyValues);
	}
}
