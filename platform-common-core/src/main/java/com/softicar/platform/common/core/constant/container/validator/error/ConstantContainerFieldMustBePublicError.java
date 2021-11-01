package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;

public class ConstantContainerFieldMustBePublicError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerFieldMustBePublicError(IConstantContainerField<T> field) {

		super(field, "Must be public.");
	}
}
