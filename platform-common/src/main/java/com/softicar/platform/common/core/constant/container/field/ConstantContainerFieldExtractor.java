package com.softicar.platform.common.core.constant.container.field;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConstantContainerFieldExtractor<T> {

	private final Class<?> containerClass;
	private final Class<T> expectedFieldType;

	public ConstantContainerFieldExtractor(Class<?> containerClass, Class<T> expectedFieldType) {

		this.containerClass = Objects.requireNonNull(containerClass);
		this.expectedFieldType = Objects.requireNonNull(expectedFieldType);
	}

	public Collection<IConstantContainerField<T>> extractFields() {

		return Arrays//
			.asList(containerClass.getDeclaredFields())
			.stream()
			.filter(this::isRelevant)
			.map(this::createContainerField)
			.collect(Collectors.toList());
	}

	private ConstantContainerField<T> createContainerField(Field field) {

		return new ConstantContainerField<>(containerClass, expectedFieldType, field);
	}

	private boolean isRelevant(Field field) {

		return expectedFieldType.isAssignableFrom(field.getType());
	}
}
