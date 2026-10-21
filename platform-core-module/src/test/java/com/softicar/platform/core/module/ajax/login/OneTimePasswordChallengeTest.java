package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import java.time.Duration;
import org.junit.Test;

public class OneTimePasswordChallengeTest extends AbstractCoreTest {

	private final AGUser user;

	public OneTimePasswordChallengeTest() {

		this.user = insertTestUser();

		AGCoreModuleInstance.getInstance().setEmailServer(insertDummyServer()).save();
	}

	@Test
	public void testSendTo() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 3);

		assertSame(user, challenge.getUser());
		assertEquals(3, challenge.getPasswordIndex());

		AGBufferedEmail email = Asserts.assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals("One-Time Password", email.getSubject());
		assertEquals(user.getEmailAddress(), email.getTo());
		Asserts.assertContains("#3", email.getContent());
		Asserts.assertContains(challenge.peekPassword().getText(), email.getContent());
	}

	@Test
	public void testValidateWithCorrectPassword() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);
		String password = challenge.peekPassword().getText();

		assertTrue(challenge.validate(password));
		assertNull(challenge.peekPassword());
	}

	@Test
	public void testValidateWithCorrectPasswordTwice() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);
		String password = challenge.peekPassword().getText();

		assertTrue(challenge.validate(password));
		assertFalse(challenge.validate(password));
	}

	@Test
	public void testValidateWithCorrectPasswordInLowerCaseAndWithWhitespace() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);
		String password = challenge.peekPassword().getText();

		assertTrue(challenge.validate(" " + password.toLowerCase() + " "));
	}

	@Test
	public void testValidateWithWrongPassword() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);
		String password = challenge.peekPassword().getText();

		assertFalse(challenge.validate("wrong"));
		assertFalse(challenge.validate(password));
	}

	@Test
	public void testValidateWithNullPassword() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);

		assertFalse(challenge.validate(null));
		assertNull(challenge.peekPassword());
	}

	@Test
	public void testValidateWithExpiredPassword() {

		var challenge = OneTimePasswordChallenge.sendTo(user, 1);
		String password = challenge.peekPassword().getText();
		testClock.add(Duration.ofSeconds(OneTimePassword.MAXIMUM_AGE_IN_SECONDS + 1));

		assertFalse(challenge.validate(password));
	}
}
