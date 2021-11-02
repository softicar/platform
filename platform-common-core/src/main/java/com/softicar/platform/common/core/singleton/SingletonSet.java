package com.softicar.platform.common.core.singleton;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of {@link Singleton} instances.
 * <p>
 * At any given time, there is one current {@link SingletonSet} per
 * {@link Thread}. It is defined by {@link CurrentSingletonSet}.
 *
 * @author Oliver Richers
 */
public class SingletonSet {

	private final Map<Singleton<?>, Object> singletons;

	/**
	 * Constructs a new {@link SingletonSet} with all {@link Singleton} values
	 * to be default.
	 */
	public SingletonSet() {

		this.singletons = new HashMap<>();
	}

	/**
	 * Constructs a new {@link SingletonSet} and inherits the {@link Singleton}
	 * values from the given {@link SingletonSet}.
	 *
	 * @param parentSet
	 *            the parent set (may be <i>null</i>)
	 */
	public SingletonSet(SingletonSet parentSet) {

		this.singletons = new HashMap<>();

		if (parentSet != null) {
			inheritValues(parentSet);
		}
	}

	<T> T getSingletonValue(Singleton<T> singleton) {

		Object value = singletons.computeIfAbsent(singleton, Singleton::getInitalValue);
		return CastUtils.cast(value);
	}

	<T> void setSingletonValue(Singleton<T> singleton, T instance) {

		singletons.put(singleton, instance);
	}

	<T> void resetSingletonValue(Singleton<T> singleton) {

		singletons.remove(singleton);
	}

	Collection<Singleton<?>> getSingletons() {

		return singletons.keySet();
	}

	private void inheritValues(SingletonSet parentSet) {

		parentSet.singletons.keySet().forEach(singleton -> inheritValue(parentSet, singleton));
	}

	private <T> void inheritValue(SingletonSet parentSet, Singleton<T> singleton) {

		T parentValue = parentSet.getSingletonValue(singleton);
		if (parentValue != null) {
			T value = singleton.getInheritedValue(parentValue);
			if (value != null) {
				setSingletonValue(singleton, value);
			}
		}
	}
}
