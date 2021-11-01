package com.softicar.platform.core.module.user.password.quality.criteria;

import org.junit.Assert;
import org.junit.Test;

public class PasswordSpecialCharacterCriterionTest extends Assert {

	@Test
	public void testWithMinimumOne() {

		IPasswordQualityCriterion criterion = new PasswordSpecialCharacterCriterion(1);
		assertFalse(criterion.isFulfilled("Foo33"));
		assertTrue(criterion.isFulfilled("Foo/33"));
	}

	@Test
	public void testWithMinimumTwo() {

		IPasswordQualityCriterion criterion = new PasswordSpecialCharacterCriterion(2);
		assertFalse(criterion.isFulfilled("Foo/33"));
		assertTrue(criterion.isFulfilled("Foo/33%"));
	}
}
