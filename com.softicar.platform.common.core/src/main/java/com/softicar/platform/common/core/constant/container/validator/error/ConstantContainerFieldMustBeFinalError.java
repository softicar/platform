package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;

public class ConstantContainerFieldMustBeFinalError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerFieldMustBeFinalError(IConstantContainerField<T> field) {

		super(field, "Must be final.");
	}
}
