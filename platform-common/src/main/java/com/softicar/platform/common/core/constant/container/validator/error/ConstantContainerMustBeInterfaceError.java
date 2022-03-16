package com.softicar.platform.common.core.constant.container.validator.error;

public class ConstantContainerMustBeInterfaceError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerMustBeInterfaceError(Class<?> containerClass) {

		super(containerClass, "Must be an interface.");
	}
}
