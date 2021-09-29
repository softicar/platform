package com.softicar.platform.common.security.crypt;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UnixCryptTest extends Assert {

	private static final String PASSWORD = "password";
	private static final String ENCRYPTED_1 = "pqlgA189RApBg";
	private static final String ENCRYPTED_2 = "/cJC3f0ijUMIo";
	private static final String ENCRYPTED_3 = "9dYXEsbbKYCAU";

	@Test
	public void testVerification() {

		assertTrue(UnixCrypt.verifyPassword(PASSWORD, ENCRYPTED_1));
		assertTrue(UnixCrypt.verifyPassword(PASSWORD, ENCRYPTED_2));
		assertTrue(UnixCrypt.verifyPassword(PASSWORD, ENCRYPTED_3));

		assertFalse(UnixCrypt.verifyPassword(PASSWORD.toUpperCase(), ENCRYPTED_1));
		assertFalse(UnixCrypt.verifyPassword("Foo", ENCRYPTED_2));
		assertFalse(UnixCrypt.verifyPassword("X" + PASSWORD, ENCRYPTED_3));
	}

	@Test
	public void testPasswordLongerThan8Chars() {

		assertTrue(UnixCrypt.verifyPassword("password2", ENCRYPTED_1));
		assertTrue(UnixCrypt.verifyPassword("passwordFoo", ENCRYPTED_2));
		assertTrue(UnixCrypt.verifyPassword("passwordWhatever", ENCRYPTED_3));
	}

	@Test
	public void testEncryption() {

		assertEquals(ENCRYPTED_1, UnixCrypt.encryptPassword(PASSWORD, "pq"));
		assertEquals(ENCRYPTED_2, UnixCrypt.encryptPassword(PASSWORD, "/c"));
		assertEquals(ENCRYPTED_3, UnixCrypt.encryptPassword(PASSWORD, "9d"));
	}

	@Test
	@Ignore
	public void testRandomSalting() {

		assertNotEquals(UnixCrypt.encryptPassword("foo"), UnixCrypt.encryptPassword("foo"));
		assertNotEquals(UnixCrypt.encryptPassword("bar"), UnixCrypt.encryptPassword("bar"));
	}

	@Test
	public void testPredefinedSalting() {

		assertEquals("ba4TuD1iozTxw", UnixCrypt.encryptPassword("foo", "bar"));
	}

	@Test
	public void testNonAsciiCharacters() {

		String salt = "xy";

		assertEquals("xyI3aMZE1oCWE", UnixCrypt.encryptPassword("äöü", salt));
		assertEquals("xy897SvF/s0tA", UnixCrypt.encryptPassword("\u0158\u0906\u09B9", salt));
		assertEquals("xySPx/XTW1u.M", UnixCrypt.encryptPassword("\u09B9\u0158\u0906", salt));
	}
}
