package com.softicar.platform.common.core.java.code.validation.output;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;

public class JavaCodeValidationOutputForTesting implements IJavaCodeValidationOuput {

	private final Collection<String> messages;

	public JavaCodeValidationOutputForTesting() {

		this.messages = new ArrayList<>();
	}

	@Override
	public void addViolation(String message) {

		messages.add(message);
	}

	@Override
	public void formatViolation(String message, Object...arguments) {

		messages.add(String.format(message, arguments));
	}

	public void assertViolationCount(int expectedCount) {

		Assert.assertEquals("violations", expectedCount, messages.size());
	}

	@Override
	public String toString() {

		return String.join("\n", messages);
	}
}
