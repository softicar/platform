package com.softicar.platform.common.core.constant.container.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class ConstantContainerField<T> implements IConstantContainerField<T> {

	private final Class<?> containerClass;
	private final Class<T> expectedFieldType;
	private final Field field;

	public ConstantContainerField(Class<?> containerClass, Class<T> expectedFieldType, Field field) {

		this.containerClass = Objects.requireNonNull(containerClass);
		this.expectedFieldType = Objects.requireNonNull(expectedFieldType);
		this.field = Objects.requireNonNull(field);
	}

	@Override
	public Class<?> getContainerClass() {

		return containerClass;
	}

	@Override
	public String getName() {

		return field.getName();
	}

	@Override
	public Class<?> getType() {

		return field.getType();
	}

	@Override
	public T getValue() {

		try {
			return expectedFieldType.cast(getFieldValueAsObject());
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public boolean isPublic() {

		return Modifier.isPublic(field.getModifiers());
	}

	@Override
	public boolean isStatic() {

		return Modifier.isStatic(field.getModifiers());
	}

	@Override
	public boolean isFinal() {

		return Modifier.isFinal(field.getModifiers());
	}

	private Object getFieldValueAsObject() throws IllegalAccessException {

		field.setAccessible(true);
		if (isStatic()) {
			return field.get(null);
		} else {
			return null;
		}
	}
}
