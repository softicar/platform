package com.softicar.platform.core.module.user.password.quality.criteria;

import org.junit.Assert;
import org.junit.Test;

public class PasswordLowercaseLetterCriterionTest extends Assert {

	@Test
	public void testWithMinimumOne() {

		IPasswordQualityCriterion criterion = new PasswordLowercaseLetterCriterion(1);
		assertFalse(criterion.isFulfilled("FOO33"));
		assertTrue(criterion.isFulfilled("FOo33"));
	}

	@Test
	public void testWithMinimumTwo() {

		IPasswordQualityCriterion criterion = new PasswordLowercaseLetterCriterion(2);
		assertFalse(criterion.isFulfilled("FOo33"));
		assertTrue(criterion.isFulfilled("Foo33"));
	}
}
