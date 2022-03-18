package com.softicar.platform.common.core.constant.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeFinalError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBePublicError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeStaticError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldOrderError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerIllegalFieldNameError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import java.util.regex.Pattern;

public abstract class AbstractConstantContainerFieldValidator<T> implements IConstantContainerValidator<T> {

	private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("([A-Z]|_[0-9])[A-Z0-9]*(_[A-Z0-9]+)*");
	protected final IConstantContainerField<T> field;
	private final IConstantContainerField<T> previousField;

	public AbstractConstantContainerFieldValidator(IConstantContainerField<T> field, IConstantContainerField<T> previousField) {

		this.field = field;
		this.previousField = previousField;
	}

	@Override
	public void validate(ConstantContainerValidatorResult<T> result) {

		validateFieldOrder(result);
		validateFieldModifiers(result);
		validateFieldName(result);
		validateFieldValues(result);
	}

	protected abstract IConstantContainerValidator<T> createFieldValueValidator(IConstantContainerField<T> field);

	private void validateFieldOrder(ConstantContainerValidatorResult<T> result) {

		if (previousField != null) {
			String fieldName = getNormalizedName(field);
			String previousFieldName = getNormalizedName(previousField);
			if (fieldName.compareTo(previousFieldName) < 0) {
				result.addError(new ConstantContainerFieldOrderError<>(field));
			}
		}
	}

	private void validateFieldModifiers(ConstantContainerValidatorResult<T> result) {

		if (!field.isPublic()) {
			result.addError(new ConstantContainerFieldMustBePublicError<>(field));
		}
		if (!field.isStatic()) {
			result.addError(new ConstantContainerFieldMustBeStaticError<>(field));
		}
		if (!field.isFinal()) {
			result.addError(new ConstantContainerFieldMustBeFinalError<>(field));
		}
	}

	private void validateFieldName(ConstantContainerValidatorResult<T> result) {

		if (!FIELD_NAME_PATTERN.matcher(field.getName()).matches()) {
			result.addError(new ConstantContainerIllegalFieldNameError<>(field, FIELD_NAME_PATTERN));
		}
	}

	private void validateFieldValues(ConstantContainerValidatorResult<T> result) {

		createFieldValueValidator(field).validate(result);
	}

	// ------------------------------ utilities ------------------------------ //

	private static String getNormalizedName(IConstantContainerField<?> field) {

		return field.getName().replace('_', ' ');
	}
}
