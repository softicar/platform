package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;

public class ConstantContainerFieldOrderError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerFieldOrderError(IConstantContainerField<T> field) {

		super(field, "Breaks alphabetical order.");
	}
}
