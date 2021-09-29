package com.softicar.platform.core.module.ajax.login;

import org.junit.Assert;
import org.junit.Test;

public class OneTimePasswordGeneratorTest extends Assert {

	@Test
	public void testGeneratePassword() {

		OneTimePassword password = new OneTimePasswordGenerator().generate(42);

		assertEquals(42, password.getIndex());
		assertEquals(OneTimePasswordGenerator.PASSWORD_LENGTH, password.getText().length());
	}

	@Test
	public void testGeneratePasswordWithMultipleCalls() {

		OneTimePassword password1 = new OneTimePasswordGenerator().generate(1);
		OneTimePassword password2 = new OneTimePasswordGenerator().generate(2);
		OneTimePassword password3 = new OneTimePasswordGenerator().generate(3);

		assertNotEquals(password1.getText(), password2.getText());
		assertNotEquals(password2.getText(), password3.getText());
		assertNotEquals(password3.getText(), password1.getText());
	}
}
