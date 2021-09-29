package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractDbCompoundKey<R, P> extends AbstractDbTableKey<R, P> {

	private final List<IDbField<R, ?>> fields = new ArrayList<>();
	private final Map<IDbField<R, ?>, Function<P, ?>> fieldGetters = new IdentityHashMap<>();

	public <V> void addField(IDbField<R, V> field, Function<P, V> getter) {

		this.fields.add(field);
		this.fieldGetters.put(field, getter);
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return fields;
	}

	@Override
	public boolean containsField(IDbField<R, ?> field) {

		return fieldGetters.containsKey(field);
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
	public <V> V getValue(IDbField<R, V> field, P keyValue) {

		Class<V> valueClass = field.getValueType().getValueClass();
		Function<P, ?> getter = fieldGetters.get(field);
		return valueClass.cast(getter.apply(keyValue));
	}

	@Override
	public int compare(P left, P right) {

		for (IDbField<R, ?> field: getFields()) {
			int cmp = compareValues(field, left, right);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}

	private <V> int compareValues(IDbField<R, V> field, P left, P right) {

		V leftValue = getValue(field, left);
		V rightValue = getValue(field, right);
		return field.getValueType().compare(leftValue, rightValue);
	}

	@Override
	public ISqlBooleanExpression<R> isEqual(P keyValue) {

		return new DbCompoundKeyIsEqualBuilder<>(this, keyValue).build();
	}

	@Override
	public ISqlBooleanExpression<R> isIn(Collection<P> keyValues) {

		return new DbCompoundKeyIsInBuilder<>(this, keyValues).build();
	}
}
