package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import java.util.Optional;

public abstract class AbstractConstantContainerValidationError<T> implements IConstantContainerValidationError<T> {

	private final IConstantContainerField<T> field;
	private final String message;

	public AbstractConstantContainerValidationError(IConstantContainerField<T> field, String message, Object...arguments) {

		this.field = field;
		this.message = String.format("%s.%s: %s", field.getContainerClass().getSimpleName(), field.getName(), String.format(message, arguments));
	}

	public AbstractConstantContainerValidationError(Class<?> containerClass, String message, Object...arguments) {

		this.field = null;
		this.message = String.format("%s: %s", containerClass.getSimpleName(), String.format(message, arguments));
	}

	@Override
	public Optional<IConstantContainerField<T>> getField() {

		return Optional.ofNullable(field);
	}

	@Override
	public String getMessage() {

		return message;
	}

	@Override
	public String toString() {

		return message;
	}
}
