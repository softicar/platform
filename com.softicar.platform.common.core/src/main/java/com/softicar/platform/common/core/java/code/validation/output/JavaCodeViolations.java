package com.softicar.platform.common.core.java.code.validation.output;

import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Aggregates all Java code violations.
 *
 * @author Oliver Richers
 */
public class JavaCodeViolations implements IJavaCodeValidationOuput {

	private final Collection<String> messages;

	public JavaCodeViolations() {

		this.messages = new ArrayList<>();
	}

	public void throwExceptionIfNotEmpty() {

		if (!messages.isEmpty()) {
			throw new JavaCodeValidationException(//
				"%s:\n\t%s",
				getClass().getSimpleName(),
				messages.stream().collect(Collectors.joining("\n\t")));
		}
	}

	@Override
	public final void addViolation(String message) {

		Objects.requireNonNull(message);

		messages.add(message);
	}

	@Override
	@SafeVarargs
	public final void formatViolation(String message, Object...arguments) {

		Objects.requireNonNull(message);
		Objects.requireNonNull(arguments);

		addViolation(String.format(message, arguments));
	}
}
