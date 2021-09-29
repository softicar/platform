package com.softicar.platform.common.core.constant.container.field;

public interface IConstantContainerField<T> {

	Class<?> getContainerClass();

	String getName();

	Class<?> getType();

	T getValue();

	boolean isPublic();

	boolean isStatic();

	boolean isFinal();
}
