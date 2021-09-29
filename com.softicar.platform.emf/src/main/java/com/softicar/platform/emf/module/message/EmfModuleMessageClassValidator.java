package com.softicar.platform.emf.module.message;

import com.softicar.platform.common.core.java.reflection.ClassHierarchyValidator;

public class EmfModuleMessageClassValidator {

	public static void validateMessageClass(IEmfModuleMessage message) {

		validateMessageClass(message.getClass());
	}

	public static void validateMessageClass(Class<? extends IEmfModuleMessage> messageClass) {

		new ClassHierarchyValidator(messageClass)//
			.assertOnlyOneInterface(IEmfModuleMessage.class)
			.assertNoSuperClass();
	}
}
