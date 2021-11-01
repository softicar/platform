package com.softicar.platform.common.core.constant.container.validator.result;

import com.softicar.platform.common.core.constant.container.validator.error.IConstantContainerValidationError;
import java.util.ArrayList;
import java.util.Collection;

public class ConstantContainerValidatorResult<T> {

	private final Collection<IConstantContainerValidationError<T>> errors;

	public ConstantContainerValidatorResult() {

		this.errors = new ArrayList<>();
	}

	public void addError(IConstantContainerValidationError<T> error) {

		errors.add(error);
	}

	public Collection<IConstantContainerValidationError<T>> getErrors() {

		return errors;
	}
}
