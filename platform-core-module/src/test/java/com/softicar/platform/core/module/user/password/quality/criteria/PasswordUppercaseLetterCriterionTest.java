package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class PasswordUppercaseLetterCriterionTest extends AbstractTest {

	@Test
	public void testWithMinimumOne() {

		IPasswordQualityCriterion criterion = new PasswordUppercaseLetterCriterion(1);
		assertFalse(criterion.isFulfilled("foo33"));
		assertTrue(criterion.isFulfilled("Foo33"));
	}

	@Test
	public void testWithMinimumTwo() {

		IPasswordQualityCriterion criterion = new PasswordUppercaseLetterCriterion(2);
		assertFalse(criterion.isFulfilled("Foo33"));
		assertTrue(criterion.isFulfilled("fOO33"));
	}
}
