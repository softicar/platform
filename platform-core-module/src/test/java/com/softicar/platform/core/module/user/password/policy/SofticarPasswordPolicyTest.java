package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class SofticarPasswordPolicyTest extends AbstractTest {

	private final SofticarPasswordPolicy policy;

	public SofticarPasswordPolicyTest() {

		this.policy = new SofticarPasswordPolicy();
	}

	@Test
	public void testEmptyPassword() {

		assertFalse(policy.isFulfilled(""));
	}

	@Test
	public void testOnlyLetterPasswords() {

		assertFalse(policy.isFulfilled("a"));
		assertFalse(policy.isFulfilled("abc"));
		assertFalse(policy.isFulfilled("abcdefghijklmnopqrstuvwxyz"));
	}

	@Test
	public void testBadPasswords() {

		assertFalse(policy.isFulfilled("123"));
		assertFalse(policy.isFulfilled("Mu"));
		assertFalse(policy.isFulfilled("secret"));
		assertFalse(policy.isFulfilled("geheim"));
		assertFalse(policy.isFulfilled("password"));
		assertFalse(policy.isFulfilled("test123456789"));
	}

	@Test
	public void testValidPasswords() {

		assertTrue(policy.isFulfilled("haba88foo/bano%"));
		assertTrue(policy.isFulfilled("trigo(puna/ko88te"));
	}
}
