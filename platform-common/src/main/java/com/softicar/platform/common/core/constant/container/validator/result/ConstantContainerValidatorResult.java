package com.softicar.platform.common.core.constant.container.validator.result;

import com.softicar.platform.common.core.constant.container.validator.error.IConstantContainerValidationError;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

	public void throwExceptionIfNotEmpty() {

		if (!errors.isEmpty()) {
			throw new JavaCodeValidationException(//
				"Validation of container class(es) failed:\n\t%s",
				errors//
					.stream()
					.map(IConstantContainerValidationError::getMessage)
					.collect(Collectors.joining("\n\t")));
		}
	}
}
