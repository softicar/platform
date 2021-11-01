package com.softicar.platform.core.module.user.password.quality.criteria;

import org.junit.Assert;
import org.junit.Test;

public class PasswordLengthCriterionTest extends Assert {

	@Test
	public void testWithMinimumOne() {

		IPasswordQualityCriterion criterion = new PasswordLengthCriterion(1);
		assertFalse(criterion.isFulfilled(""));
		assertTrue(criterion.isFulfilled("f"));
		assertTrue(criterion.isFulfilled("foo"));
	}

	@Test
	public void testWithMinimumTwo() {

		IPasswordQualityCriterion criterion = new PasswordLengthCriterion(2);
		assertFalse(criterion.isFulfilled("f"));
		assertTrue(criterion.isFulfilled("fo"));
		assertTrue(criterion.isFulfilled("foo"));
	}

	@Test
	public void testWithMinimumFive() {

		IPasswordQualityCriterion criterion = new PasswordLengthCriterion(5);
		assertFalse(criterion.isFulfilled("foo3"));
		assertTrue(criterion.isFulfilled("foo33"));
		assertTrue(criterion.isFulfilled("foo33bar"));
	}
}
