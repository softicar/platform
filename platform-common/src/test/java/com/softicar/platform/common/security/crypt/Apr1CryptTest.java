package com.softicar.platform.common.security.crypt;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class Apr1CryptTest extends AbstractTest {

	private static final String PASSWORD = "password";
	private static final String ENCRYPTED_1 = "$apr1$BSxp3jrq$E7GlqIO7FZFGNrx9WzcXf.";
	private static final String ENCRYPTED_2 = "$apr1$u6aYdZUI$tQZUNggq7y54lCPpbLdqs0";
	private static final String ENCRYPTED_3 = "$apr1$QWRHRbep$oDcE6OA9Z7Iv55jS.CyET.";

	@Test
	public void testVerification() {

		assertTrue(Apr1Crypt.verifyPassword(PASSWORD, ENCRYPTED_1));
		assertTrue(Apr1Crypt.verifyPassword(PASSWORD, ENCRYPTED_2));
		assertTrue(Apr1Crypt.verifyPassword(PASSWORD, ENCRYPTED_3));

		assertFalse(Apr1Crypt.verifyPassword(PASSWORD.toUpperCase(), ENCRYPTED_1));
		assertFalse(Apr1Crypt.verifyPassword(PASSWORD + "2", ENCRYPTED_2));
		assertFalse(Apr1Crypt.verifyPassword("X" + PASSWORD, ENCRYPTED_3));
	}

	@Test
	public void testEncryption() {

		assertEquals(ENCRYPTED_1, Apr1Crypt.encryptPassword(PASSWORD, "BSxp3jrq"));
		assertEquals(ENCRYPTED_2, Apr1Crypt.encryptPassword(PASSWORD, "u6aYdZUI"));
		assertEquals(ENCRYPTED_3, Apr1Crypt.encryptPassword(PASSWORD, "QWRHRbep"));
	}

	@Test
	public void testRandomSalting() {

		assertNotEquals(Apr1Crypt.encryptPassword("foo"), Apr1Crypt.encryptPassword("foo"));
		assertNotEquals(Apr1Crypt.encryptPassword("bar"), Apr1Crypt.encryptPassword("bar"));
	}

	@Test
	public void testPredefinedSalting() {

		assertEquals("$apr1$bar$H4L0kySLjLaOUVIetfOYc.", Apr1Crypt.encryptPassword("foo", "bar"));
	}

	@Test
	public void testNonAsciiCharacters() {

		String salt = "xy";

		assertEquals("$apr1$xy$bTlbQhlovHgWt5zfHWEHi.", Apr1Crypt.encryptPassword("äöü", salt));
		assertEquals("$apr1$xy$J0I3z1F8OvHYgTZjHjokD.", Apr1Crypt.encryptPassword("\u0158\u0906\u09B9", salt));
		assertEquals("$apr1$xy$2TtX7ir.9z/m1ERlNxXiR/", Apr1Crypt.encryptPassword("\u09B9\u0158\u0906", salt));
	}
}
