package com.softicar.platform.core.module.user.password.quality.criteria;

import org.junit.Assert;
import org.junit.Test;

public class PasswordDistinctCharactersCriterionTest extends Assert {

	@Test
	public void testMinimumOfOne() {

		PasswordDistinctCharactersCriterion criterion = new PasswordDistinctCharactersCriterion(1);
		assertFalse(criterion.isFulfilled(""));
		assertTrue(criterion.isFulfilled("f"));
	}

	@Test
	public void testMinimumOfTwo() {

		PasswordDistinctCharactersCriterion criterion = new PasswordDistinctCharactersCriterion(2);
		assertFalse(criterion.isFulfilled("fff"));
		assertTrue(criterion.isFulfilled("foo"));
	}

	@Test
	public void testMinimumOfThree() {

		PasswordDistinctCharactersCriterion criterion = new PasswordDistinctCharactersCriterion(3);
		assertFalse(criterion.isFulfilled("foo"));
		assertTrue(criterion.isFulfilled("bar"));
	}
}
