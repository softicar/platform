package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import java.util.regex.Pattern;

public class ConstantContainerIllegalFieldNameError<T> extends AbstractConstantContainerValidationError<T> {

	public ConstantContainerIllegalFieldNameError(IConstantContainerField<T> field, Pattern pattern) {

		super(field, "Name must match regex '%s'.", pattern.pattern());
	}
}
