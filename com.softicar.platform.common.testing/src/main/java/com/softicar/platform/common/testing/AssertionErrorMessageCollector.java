package com.softicar.platform.common.testing;

import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.Collection;

public class AssertionErrorMessageCollector {

	private final Collection<String> errorMessages;

	public AssertionErrorMessageCollector() {

		this.errorMessages = new ArrayList<>();
	}

	public Collection<String> getErrorMessages() {

		return errorMessages;
	}

	public AssertionErrorMessageCollector add(String message) {

		return add(message, new Object[0]);
	}

	public AssertionErrorMessageCollector addAll(Collection<String> messages) {

		messages.forEach(this::add);
		return this;
	}

	public AssertionErrorMessageCollector addAll(AssertionErrorMessageCollector other) {

		return addAll(other.getErrorMessages());
	}

	public AssertionErrorMessageCollector add(String message, Object...args) {

		this.errorMessages.add(String.format(message, args));
		return this;
	}

	public void throwIfNecessary() {

		throwIfNecessary(null);
	}

	public void throwIfNecessary(String descriptiveMessage, Object...args) {

		if (!errorMessages.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append(String.format("Encountered %s errors: %s\n", errorMessages.size(), createDescription(descriptiveMessage, args)));
			message.append(Imploder.implode(errorMessages, "\n"));
			throw new AssertionError(message.toString());
		}
	}

	private String createDescription(String headerMessage, Object...args) {

		if (headerMessage != null) {
			return String.format(headerMessage, args);
		} else {
			return "";
		}
	}
}
