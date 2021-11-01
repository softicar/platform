package com.softicar.platform.common.core.constant.container.validator;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustBeInterfaceError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotDeclareMethodError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotExtendOtherClassError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.java.code.validation.output.JavaCodeViolations;
import com.softicar.platform.common.core.java.code.validator.JavaClassValidator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates a given "constant container" class.
 * <p>
 * <ul>
 * <li>The class must be an interface.</li>
 * <li>The class may not have a super class.</li>
 * <li>The class may not declare any methods.</li>
 * </ul>
 * <ul>
 * <li>All fields must be <b>public</b> and <b>static</b> and <b>final</b>.</li>
 * <li>All fields must be in alphabetic order.</li>
 * <li>All field names must obey the Java conventions for constants.</li>
 * </ul>
 * <p>
 * TODO Improve and use {@link JavaClassValidator} instead of
 * {@link ConstantContainerValidatorResult}.
 * <p>
 * TODO Improve and use {@link JavaCodeViolations} instead of redundant
 * implementations of
 * {@link #validateClassType(ConstantContainerValidatorResult)} /
 * {@link #validateSuperClass(ConstantContainerValidatorResult)} /
 * {@link #validateMethods(ConstantContainerValidatorResult)}.
 *
 * @param <T>
 *            the expected type of the fields in the constant container class
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractConstantContainerValidator<T> implements IConstantContainerValidator<T> {

	private final List<IConstantContainerValidator<T>> additionalValidators;
	protected final Class<?> containerClass;
	protected final Class<T> expectedFieldType;

	public AbstractConstantContainerValidator(Class<?> containerClass, Class<T> expectedFieldType) {

		this.additionalValidators = new ArrayList<>();
		this.containerClass = containerClass;
		this.expectedFieldType = expectedFieldType;
	}

	@Override
	public void validate(ConstantContainerValidatorResult<T> result) {

		validateClassType(result);
		validateSuperClass(result);
		validateMethods(result);
		validateFields(result);
		validateAdditionally(result);
	}

	public void addAdditionalValidator(IConstantContainerValidator<T> additionalValidator) {

		additionalValidators.add(additionalValidator);
	}

	protected abstract IConstantContainerValidator<T> createFieldValidator(IConstantContainerField<T> currentField, IConstantContainerField<T> previousField);

	protected ConstantContainerFieldExtractor<T> createFieldExtractor() {

		return new ConstantContainerFieldExtractor<>(containerClass, expectedFieldType);
	}

	private void validateClassType(ConstantContainerValidatorResult<T> result) {

		if (!containerClass.isInterface()) {
			result.addError(new ConstantContainerMustBeInterfaceError<>(containerClass));
		}
	}

	private void validateSuperClass(ConstantContainerValidatorResult<T> result) {

		if (containerClass.getSuperclass() != null) {
			result.addError(new ConstantContainerMustNotExtendOtherClassError<>(containerClass));
		}
	}

	private void validateMethods(ConstantContainerValidatorResult<T> result) {

		for (Method method: containerClass.getDeclaredMethods()) {
			result.addError(new ConstantContainerMustNotDeclareMethodError<>(containerClass, method));
		}
	}

	private void validateFields(ConstantContainerValidatorResult<T> result) {

		var extractor = createFieldExtractor();
		IConstantContainerField<T> previousField = null;
		for (IConstantContainerField<T> field: extractor.extractFields()) {
			createFieldValidator(field, previousField).validate(result);
			previousField = field;
		}
	}

	private void validateAdditionally(ConstantContainerValidatorResult<T> result) {

		additionalValidators.forEach(validator -> validator.validate(result));
	}
}
