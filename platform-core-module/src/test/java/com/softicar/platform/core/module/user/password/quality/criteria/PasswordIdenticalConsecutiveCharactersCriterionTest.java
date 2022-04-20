package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class PasswordIdenticalConsecutiveCharactersCriterionTest extends AbstractTest {

	@Test(expected = Exception.class)
	public void testWithMaximumZero() {

		@SuppressWarnings("unused")
		PasswordIdenticalConsecutiveCharactersCriterion criterion = new PasswordIdenticalConsecutiveCharactersCriterion(0);
	}

	@Test
	public void testWithMaximumOne() {

		IPasswordQualityCriterion criterion = new PasswordIdenticalConsecutiveCharactersCriterion(1);
		assertFalse(criterion.isFulfilled("foo"));
		assertTrue(criterion.isFulfilled("bar"));
	}

	@Test
	public void testWithMaximumTwo() {

		IPasswordQualityCriterion criterion = new PasswordIdenticalConsecutiveCharactersCriterion(2);
		assertFalse(criterion.isFulfilled("fooo"));
		assertTrue(criterion.isFulfilled("foo"));
		assertTrue(criterion.isFulfilled("bar"));
	}
}
