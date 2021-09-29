package com.softicar.platform.common.core.constant.container.validator.result;

import com.softicar.platform.common.core.constant.container.validator.error.IConstantContainerValidationError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConstantContainerValidatorResultAsserter {

	private final ArrayList<IConstantContainerValidationError<?>> errors;

	public ConstantContainerValidatorResultAsserter(ConstantContainerValidatorResult<?> result) {

		this.errors = new ArrayList<>(result.getErrors());
	}

	public void assertNoErrors() {

		if (!errors.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append(String.format("Expected no more errors but got %s:\n", errors.size()));
			errors
				.forEach(
					error -> message//
						.append("\t")
						.append(error.getMessage())
						.append("\n"));
			throw new AssertionError(message.toString());
		}
	}

	public <T extends IConstantContainerValidationError<?>> ConstantContainerValidatorResultAsserter assertError(Class<T> errorClass) {

		return assertError(//
			error -> errorClass.isInstance(error),
			() -> String.format("Expected error %s.", errorClass.getSimpleName()));
	}

	public <T extends IConstantContainerValidationError<?>> ConstantContainerValidatorResultAsserter assertError(String fieldName, Class<T> errorClass) {

		return assertError(errorClass, fieldName);
	}

	public <T extends IConstantContainerValidationError<?>> ConstantContainerValidatorResultAsserter assertError(Class<T> errorClass, String fieldName) {

		return assertError(//
			error -> isOnField(error, fieldName) && errorClass.isInstance(error),
			() -> String.format("Expected error %s on field %s.", errorClass.getSimpleName(), fieldName));
	}

	private boolean isOnField(IConstantContainerValidationError<?> error, String fieldName) {

		return error.getField().map(field -> field.getName().equals(fieldName)).orElse(false);
	}

	private ConstantContainerValidatorResultAsserter assertError(Predicate<IConstantContainerValidationError<?>> predicate, Supplier<String> errorSupplier) {

		Iterator<IConstantContainerValidationError<?>> iterator = errors.iterator();
		while (iterator.hasNext()) {
			IConstantContainerValidationError<?> error = iterator.next();
			if (predicate.test(error)) {
				iterator.remove();
				return this;
			}
		}
		throw new AssertionError(errorSupplier.get());
	}
}
