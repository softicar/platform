package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class PasswordDigitCharacterCriterionTest extends AbstractTest {

	@Test
	public void testWithMinimumOne() {

		IPasswordQualityCriterion criterion = new PasswordDigitCharacterCriterion(1);
		assertFalse(criterion.isFulfilled("foo"));
		assertTrue(criterion.isFulfilled("foo3"));
	}

	@Test
	public void testWithMinimumTwo() {

		IPasswordQualityCriterion criterion = new PasswordDigitCharacterCriterion(2);
		assertFalse(criterion.isFulfilled("foo3"));
		assertTrue(criterion.isFulfilled("foo35"));
	}
}
