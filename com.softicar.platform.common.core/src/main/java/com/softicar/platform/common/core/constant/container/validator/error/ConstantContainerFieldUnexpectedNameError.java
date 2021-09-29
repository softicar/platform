package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;

public class ConstantContainerFieldUnexpectedNameError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerFieldUnexpectedNameError(IConstantContainerField<T> field, String expectedName) {

		super(field, "Expected name: %s", expectedName);
	}
}
