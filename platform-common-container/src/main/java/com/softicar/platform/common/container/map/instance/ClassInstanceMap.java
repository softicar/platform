package com.softicar.platform.common.container.map.instance;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class maps classes to an single instance of that class.
 *
 * @author Oliver Richers
 */
public class ClassInstanceMap<T> {

	private final Map<Class<?>, T> map = new HashMap<>();

	public <I extends T> void putInstance(I instance) {

		Objects.requireNonNull(instance);
		map.put(instance.getClass(), instance);
	}

	public <I extends T> void putInstanceIfAbsent(I instance) {

		Objects.requireNonNull(instance);
		map.putIfAbsent(instance.getClass(), instance);
	}

	public <I extends T> Optional<I> getOptional(Class<I> instanceClass) {

		Objects.requireNonNull(instanceClass);
		Object instance = map.get(instanceClass);
		return Optional.ofNullable(instanceClass.cast(instance));
	}

	public <I extends T> I getOrPutInstance(Class<I> instanceClass, Supplier<I> instanceFactory) {

		Object instance = map.get(instanceClass);
		if (instance != null) {
			return instanceClass.cast(instance);
		} else {
			I newInstance = instanceFactory.get();
			assertCorrectClass(instanceClass, newInstance);
			putInstance(newInstance);
			return newInstance;
		}
	}

	public Collection<T> getAll() {

		return map.values();
	}

	private static <I> void assertCorrectClass(Class<I> instanceClass, I newInstance) {

		if (newInstance.getClass() != instanceClass) {
			throw new SofticarDeveloperException(//
				"Constructed data is of type '%s' but should be '%s'.",
				newInstance.getClass().getCanonicalName(),
				instanceClass.getCanonicalName());
		}
	}
}
