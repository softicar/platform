package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.Set;

/**
 * Base class for all transient fields of accumulative type.
 *
 * @param <O>
 *            the type of the field owning object class
 * @param <V>
 *            the type of the field values
 * @param <E>
 *            the type of the field value elements
 * @author Oliver Richers
 */
public abstract class AbstractTransientAccumulativeField<O extends IDbTableRow<O, ?>, V, E> extends AbstractTransientField<O, V> {

	public AbstractTransientAccumulativeField(ITransientFieldValueType<V> valueType) {

		super(valueType);
	}

	@Override
	protected final void loadValues(Map<O, V> objectValueMap) {

		loadValues(objectValueMap.keySet(), (object, element) -> {
			V value = objectValueMap.get(object);
			objectValueMap.put(object, combine(value != null? value : getDefaultValue(), element));
		});
	}

	protected abstract V combine(V value, E element);

	protected abstract void loadValues(Set<O> objects, IValueAccumulator<O, E> accumulator);
}
