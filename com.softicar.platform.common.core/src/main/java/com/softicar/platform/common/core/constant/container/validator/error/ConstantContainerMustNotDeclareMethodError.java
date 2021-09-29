package com.softicar.platform.common.core.constant.container.validator.error;

import java.lang.reflect.Method;

public class ConstantContainerMustNotDeclareMethodError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerMustNotDeclareMethodError(Class<?> containerClass, Method method) {

		super(containerClass, "Must not declare method '%s'.", method.getName());
	}
}
