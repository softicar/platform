package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import java.time.Duration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OneTimePasswordSessionAttributeTest extends Assert {

	private final TestClock clock;
	private final OneTimePasswordSessionAttribute sessionAttribute;

	public OneTimePasswordSessionAttributeTest() {

		this.clock = new TestClock();
		this.sessionAttribute = new OneTimePasswordSessionAttribute();
	}

	@Before
	public void before() {

		CurrentClock.set(clock);
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testConstructor() {

		assertNull(sessionAttribute.peekPassword());
	}

	@Test
	public void testGenerateAndStorePassword() {

		OneTimePassword password = sessionAttribute.generateAndStorePasswort();

		assertSame(password, sessionAttribute.peekPassword());
		assertEquals(1, password.getIndex());
		assertEquals(OneTimePasswordGenerator.PASSWORD_LENGTH, password.getText().length());
	}

	@Test
	public void testGenerateAndStorePasswordWithMultipleCalls() {

		OneTimePassword password1 = sessionAttribute.generateAndStorePasswort();
		OneTimePassword password2 = sessionAttribute.generateAndStorePasswort();
		OneTimePassword password3 = sessionAttribute.generateAndStorePasswort();

		assertSame(password3, sessionAttribute.peekPassword());
		assertEquals(1, password1.getIndex());
		assertEquals(2, password2.getIndex());
		assertEquals(3, password3.getIndex());
		assertNotEquals(password1.getText(), password2.getText());
		assertNotEquals(password2.getText(), password3.getText());
		assertNotEquals(password3.getText(), password1.getText());
	}

	@Test
	public void testValidatePasswordWithCorrectPassword() {

		OneTimePassword password = sessionAttribute.generateAndStorePasswort();

		boolean result = sessionAttribute.validatePassword(password.getText());

		assertTrue(result);
		assertNull(sessionAttribute.peekPassword());
	}

	@Test
	public void testValidatePasswordWithIncorrectPassword() {

		sessionAttribute.generateAndStorePasswort();
		boolean result = sessionAttribute.validatePassword("xxx");

		assertFalse(result);
		assertNull(sessionAttribute.peekPassword());
	}

	@Test
	public void testValidatePasswordWithObsoletePassword() {

		OneTimePassword obsoletePassword = sessionAttribute.generateAndStorePasswort();
		sessionAttribute.generateAndStorePasswort();

		boolean result = sessionAttribute.validatePassword(obsoletePassword.getText());

		assertFalse(result);
		assertNull(sessionAttribute.peekPassword());
	}

	@Test
	public void testValidatePasswordWithExpiredPassword() {

		OneTimePassword password = sessionAttribute.generateAndStorePasswort();
		clock.add(Duration.ofSeconds(OneTimePassword.MAXIMUM_AGE_IN_SECONDS + 1));

		boolean result = sessionAttribute.validatePassword(password.getText());

		assertFalse(result);
		assertNull(sessionAttribute.peekPassword());
	}
}
