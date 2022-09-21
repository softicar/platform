package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * Defines a transient field with values of type {@link Object}.
 * <p>
 * Please note that null-values are not supported. If the values are optional,
 * use {@link AbstractTransientOptionalField}.
 *
 * @param <O>
 *            the type of the field owning object class
 * @param <V>
 *            the type of the field values
 * @author Oliver Richers
 */
public abstract class AbstractTransientObjectField<O extends IDbTableRow<O, ?>, V> extends AbstractTransientField<O, V> {

	public AbstractTransientObjectField(ITransientFieldValueType<V> valueType) {

		super(valueType);
	}

	public AbstractTransientObjectField(Class<V> valueClass) {

		super(new TransientFieldValueType<>(valueClass));
	}

	public AbstractTransientObjectField(Class<V> valueClass, Comparator<V> comparator) {

		super(new TransientFieldValueType<>(valueClass, comparator));
	}

	@Override
	protected final void loadValues(Map<O, V> objectValueMap) {

		loadValues(objectValueMap.keySet(), (object, value) -> objectValueMap.put(object, value));
	}

	protected abstract void loadValues(Set<O> objects, IValueSetter<O, V> setter);
}
