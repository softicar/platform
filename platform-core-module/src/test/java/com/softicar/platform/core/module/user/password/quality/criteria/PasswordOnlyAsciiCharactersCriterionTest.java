package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class PasswordOnlyAsciiCharactersCriterionTest extends AbstractTest {

	@Test
	public void testWithOnlyAscii() {

		IPasswordQualityCriterion criterion = new PasswordOnlyAsciiCharactersCriterion();
		assertTrue(criterion.isFulfilled("onlyLETTERS"));
		assertTrue(criterion.isFulfilled("0123456789"));
		assertTrue(criterion.isFulfilled("letters1234`-=[]\\;',./~!@#$%^&*()_+{}|:\"<>?"));
	}

	@Test
	public void testWithNonAscii() {

		IPasswordQualityCriterion criterion = new PasswordSpecialCharacterCriterion(2);
		assertFalse(criterion.isFulfilled("fooü"));
		assertFalse(criterion.isFulfilled("barẽî"));
	}
}
