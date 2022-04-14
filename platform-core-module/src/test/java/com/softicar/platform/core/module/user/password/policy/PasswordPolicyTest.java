package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordDigitCharacterCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordLengthCriterion;
import org.junit.Test;

public class PasswordPolicyTest extends AbstractTest {

	private final PasswordPolicy policy = new PasswordPolicy();

	@Test
	public void testEmptyPolicy() {

		assertTrue(policy.isFulfilled(""));
		assertTrue(policy.isFulfilled("foo"));
	}

	@Test(expected = NullPointerException.class)
	public void testNullPassword() {

		policy.addQualityCriterion(new PasswordLengthCriterion(3));
		policy.isFulfilled(null);
	}

	@Test
	public void testPolicyWithMandatoryLength() {

		policy.addQualityCriterion(new PasswordLengthCriterion(3));
		assertFalse(policy.isFulfilled(""));
		assertFalse(policy.isFulfilled("f"));
		assertFalse(policy.isFulfilled("fo"));
		assertTrue(policy.isFulfilled("foo"));
	}

	@Test
	public void testPolicyWithMandatoryDigits() {

		policy.addQualityCriterion(new PasswordDigitCharacterCriterion(3));
		assertFalse(policy.isFulfilled(""));
		assertFalse(policy.isFulfilled("foo"));
		assertFalse(policy.isFulfilled("foo12"));
		assertTrue(policy.isFulfilled("foo123"));
		assertTrue(policy.isFulfilled("1x2x3"));
	}
}
