package com.softicar.platform.common.code.validator;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.ArrayList;
import java.util.Collection;

class PlatformCodeViolationCollection {

	private final Collection<String> violationMessages;

	public PlatformCodeViolationCollection() {

		this.violationMessages = new ArrayList<>();
	}

	public void addViolation(String message, Object...arguments) {

		violationMessages.add(String.format(message, arguments));
	}

	public void addClassHasMainMethodViolation(JavaClassName className) {

		addViolation("Class may not have `main` method: %s", className);
	}

	public void addClassUsesForbiddenClassViolation(JavaClassName className, JavaClassName forbiddenClass) {

		addViolation("Reference to forbidden class: %s --> %s", className, forbiddenClass);
	}

	public boolean isEmpty() {

		return violationMessages.isEmpty();
	}

	public Collection<String> getMessages() {

		return violationMessages;
	}
}
