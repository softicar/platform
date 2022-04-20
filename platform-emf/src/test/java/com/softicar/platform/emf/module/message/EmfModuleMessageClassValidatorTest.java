package com.softicar.platform.emf.module.message;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.java.reflection.ClassHierarchyValidationException;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EmfModuleMessageClassValidatorTest extends AbstractTest {

	@Test
	public void testValidMessageClass() {

		EmfModuleMessageClassValidator.validateMessageClass(ValidMessage.class);
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testMessageExtendingOtherClass() {

		EmfModuleMessageClassValidator.validateMessageClass(MessageExtendingOtherClass.class);
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testMessageImplementingOtherInterface() {

		EmfModuleMessageClassValidator.validateMessageClass(MessageImplementingOtherInterface.class);
	}

	private static class ValidMessage implements IEmfModuleMessage {

		// nothing to add
	}

	private static class MessageExtendingOtherClass extends ValidMessage {

		// nothing to add
	}

	private static class MessageImplementingOtherInterface implements IEmfModuleMessage, INullaryVoidFunction {

		@Override
		public void apply() {

			// nothing to add
		}
	}
}
