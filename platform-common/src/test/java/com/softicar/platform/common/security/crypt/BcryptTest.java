package com.softicar.platform.common.security.crypt;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class BcryptTest extends AbstractTest {

	private static final String PASSWORD = "password";
	private static final String ENCRYPTED_1 = "$2a$05$SlbuZ9bDYvS2pIz/dJNVROZVoHN/R9wyC8qNo7k88ptGKo5sRi3YG";
	private static final String ENCRYPTED_2 = "$2a$05$i.TBpIkpbH.XLL4sQCDb.OJVXVlJokLSr3jMXaawfcMGFAqxjdWSW";
	private static final String ENCRYPTED_3 = "$2a$05$nwD4jTDcboxI2aOLI1UXY.pow0np4ygwtat8MurFCYJ/MuvW9HlUe";

	@Test
	public void testVerification() {

		assertTrue(Bcrypt.verifyPassword(PASSWORD, ENCRYPTED_1));
		assertTrue(Bcrypt.verifyPassword(PASSWORD, ENCRYPTED_2));
		assertTrue(Bcrypt.verifyPassword(PASSWORD, ENCRYPTED_3));

		assertFalse(Bcrypt.verifyPassword(PASSWORD.toUpperCase(), ENCRYPTED_1));
		assertFalse(Bcrypt.verifyPassword(PASSWORD + "2", ENCRYPTED_2));
		assertFalse(Bcrypt.verifyPassword("X" + PASSWORD, ENCRYPTED_3));
	}

	@Test
	public void testEncryption() {

		assertEquals(ENCRYPTED_1, Bcrypt.encryptPassword(PASSWORD, "$2a$05$SlbuZ9bDYvS2pIz/dJNVRO"));
		assertEquals(ENCRYPTED_2, Bcrypt.encryptPassword(PASSWORD, "$2a$05$i.TBpIkpbH.XLL4sQCDb.O"));
		assertEquals(ENCRYPTED_3, Bcrypt.encryptPassword(PASSWORD, "$2a$05$nwD4jTDcboxI2aOLI1UXY."));
	}

	@Test
	public void testRandomSalting() {

		assertNotEquals(Bcrypt.encryptPassword("foo"), Bcrypt.encryptPassword("foo"));
		assertNotEquals(Bcrypt.encryptPassword("bar"), Bcrypt.encryptPassword("bar"));
	}

	@Test
	public void testPredefinedSalting() {

		assertEquals("$2a$05$AdPmCdG0uSESwfVXwUA7Zurxtv/5LKTann5XPVcFCeC0C2kVZLiRK", Bcrypt.encryptPassword("foo", "$2a$05$AdPmCdG0uSESwfVXwUA7Zu"));
	}

	@Test
	public void testNonAsciiCharacters() {

		String salt = "$2a$05$o35Ox78wpg6R4LwDQN7RYe";

		assertEquals("$2a$05$o35Ox78wpg6R4LwDQN7RYe8jonth7W1on5EnsJ0YEtH81qSR3kIt6", Bcrypt.encryptPassword("äöü", salt));
		assertEquals("$2a$05$o35Ox78wpg6R4LwDQN7RYeG1qyp043PZy4A0CzXufIJqS5eiUN/cy", Bcrypt.encryptPassword("\u0158\u0906\u09B9", salt));
		assertEquals("$2a$05$o35Ox78wpg6R4LwDQN7RYeE5n1t7.IEXf2YMF64CByA2rGsjac1eq", Bcrypt.encryptPassword("\u09B9\u0158\u0906", salt));
	}
}
