package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbBasicTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The base class of all transient fields.
 *
 * @param <O>
 *            the type of the field owning object class
 * @param <V>
 *            the type of the field values
 * @author Oliver Richers
 */
abstract class AbstractTransientField<O extends IDbBasicTableRow<O>, V> extends AbstractDbTransientField<O, V> implements ITransientField<O, V> {

	private final ITransientFieldValueType<V> valueType;

	public AbstractTransientField(ITransientFieldValueType<V> valueType) {

		this.valueType = valueType;
	}

	@Override
	public ITransientFieldValueType<V> getValueType() {

		return valueType;
	}

	/**
	 * Returns the value of this field for the given object.
	 *
	 * @param object
	 *            the object to return the value for
	 * @return the value (never null)
	 */
	@Override
	public final V getValue(O object) {

		V value = readValueFromCache(object);
		if (value == null || object.dataChanged()) {
			reloadAll(Collections.singleton(object));
			value = readValueFromCache(object);
		}
		return value;
	}

	/**
	 * Invalidates the value of this field for the given object.
	 * <p>
	 * On the next call to {@link #getValue} the value will be reloaded.
	 *
	 * @param object
	 *            the object to invalidate the value for
	 */
	@Override
	public final void invalidate(O object) {

		writeValueToCache(object, null);
	}

	/**
	 * Invalidates the values of this field for the given objects.
	 * <p>
	 * On the next call to {@link #getValue} the value will be reloaded.
	 *
	 * @param objects
	 *            the objects to invalidate the values for
	 */
	@Override
	public final void invalidateAll(Collection<? extends O> objects) {

		objects.forEach(this::invalidate);
	}

	/**
	 * Pre-fetches the values of this field for the given objects.
	 * <p>
	 * Only those values that have not been loaded before will be loaded.
	 *
	 * @param objects
	 *            the objects to pre-fetch the values for
	 */
	@Override
	public final void prefetchAll(Collection<? extends O> objects) {

		reloadAll(
			objects//
				.stream()
				.filter(object -> readValueFromCache(object) == null)
				.collect(Collectors.toList()));
	}

	@Override
	public void reload(O object) {

		reloadAll(Collections.singleton(object));
	}

	/**
	 * Reloads the values of this field for the given objects.
	 * <p>
	 * All previously loaded values of the objects will be overwritten.
	 *
	 * @param objects
	 *            the objects to pre-fetch the values for
	 */
	@Override
	public final void reloadAll(Collection<? extends O> objects) {

		if (!objects.isEmpty()) {
			// initialize object to value map
			Map<O, V> objectValueMap = new IdentityHashMap<>();
			for (O object: objects) {
				objectValueMap.put(object, null);
			}

			// load values
			loadValues(objectValueMap);

			// assign values to objects
			for (O object: objects) {
				V value = objectValueMap.get(object);
				writeValueToCache(object, value != null? value : getDefaultValue());
			}
		}
	}

	protected abstract void loadValues(Map<O, V> objectValueMap);

	/**
	 * @return the default value for this transient field (never null)
	 */
	protected abstract V getDefaultValue();
}
