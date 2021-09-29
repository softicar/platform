package com.softicar.platform.common.code.classpath.metadata;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

class TransitivelyRelatedClassesFinder {

	private final Function<Class<?>, Collection<Class<?>>> directlyRelatedClassesGetter;

	public TransitivelyRelatedClassesFinder(Function<Class<?>, Collection<Class<?>>> directlyRelatedClassesGetter) {

		this.directlyRelatedClassesGetter = directlyRelatedClassesGetter;
	}

	public Set<Class<?>> findAll(Collection<Class<?>> inputClasses) {

		Set<Class<?>> result = new HashSet<>();
		Set<Class<?>> done = new HashSet<>();
		Deque<Class<?>> stack = new ArrayDeque<>(inputClasses);

		while (!stack.isEmpty()) {
			Class<?> inputClass = stack.pop();
			if (done.add(inputClass)) {
				Collection<Class<?>> relatedClasses = directlyRelatedClassesGetter.apply(inputClass);
				result.addAll(relatedClasses);
				stack.addAll(relatedClasses);
			}
		}

		result.removeAll(inputClasses);
		return result;
	}
}
