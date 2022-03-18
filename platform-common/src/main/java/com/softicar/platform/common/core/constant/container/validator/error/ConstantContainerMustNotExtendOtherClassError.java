package com.softicar.platform.common.core.constant.container.validator.error;

public class ConstantContainerMustNotExtendOtherClassError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerMustNotExtendOtherClassError(Class<?> containerClass) {

		super(containerClass, "Must not have a super class.");
	}
}
