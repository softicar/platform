package com.softicar.platform.common.core.constant.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldValueUndefinedError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import java.util.Optional;

public abstract class AbstractConstantContainerFieldValueValidator<T> implements IConstantContainerValidator<T> {

	protected final IConstantContainerField<T> field;

	public AbstractConstantContainerFieldValueValidator(IConstantContainerField<T> field) {

		this.field = field;
	}

	@Override
	public final void validate(ConstantContainerValidatorResult<T> result) {

		getFieldValue(result).ifPresent(fieldValue -> validate(result, fieldValue));
	}

	protected abstract void validate(ConstantContainerValidatorResult<T> result, T fieldValue);

	private Optional<T> getFieldValue(ConstantContainerValidatorResult<T> result) {

		T value = field.getValue();
		if (value != null) {
			return Optional.of(value);
		} else {
			result.addError(new ConstantContainerFieldValueUndefinedError<>(field));
			return Optional.empty();
		}
	}
}
