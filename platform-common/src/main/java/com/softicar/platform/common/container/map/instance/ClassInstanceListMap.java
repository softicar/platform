package com.softicar.platform.common.container.map.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class maps classes to a {@link List} of instances of that class.
 *
 * @author Oliver Richers
 */
public class ClassInstanceListMap {

	private final Map<Class<?>, List<?>> map;

	public ClassInstanceListMap() {

		this.map = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> void add(T instance) {

		var list = (List<T>) map.computeIfAbsent(instance.getClass(), dummy -> new ArrayList<>());
		list.add(instance);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getInstances(Class<T> instancesClass) {

		return (List<T>) map.getOrDefault(instancesClass, Collections.emptyList());
	}
}
