package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;

public class ConstantContainerFieldValueUndefinedError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerFieldValueUndefinedError(IConstantContainerField<T> field) {

		super(field, "Field value was undefined.");
	}
}
