package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class ComposedPasswordQualityCriterionTest extends AbstractTest {

	private final ComposedPasswordQualityCriterion criterion;

	public ComposedPasswordQualityCriterionTest() {

		this.criterion = new ComposedPasswordQualityCriterion();
		this.criterion.addSubCriterion(new PasswordLowercaseLetterCriterion(3));
		this.criterion.addSubCriterion(new PasswordUppercaseLetterCriterion(3));
	}

	@Test
	public void testWithMinimumOfZero() {

		this.criterion.setMinimumSubCriteriaCount(0);
		assertTrue(criterion.isFulfilled(""));
		assertTrue(criterion.isFulfilled("foo"));
	}

	@Test
	public void testWithMinimumOfOne() {

		this.criterion.setMinimumSubCriteriaCount(1);
		assertFalse(criterion.isFulfilled(""));
		assertFalse(criterion.isFulfilled("foBA"));
		assertTrue(criterion.isFulfilled("foo"));
		assertTrue(criterion.isFulfilled("BAR"));
		assertTrue(criterion.isFulfilled("fooBAR"));
		assertTrue(criterion.isFulfilled("fOoBaR"));
	}

	@Test
	public void testWithMinimumOfTwo() {

		this.criterion.setMinimumSubCriteriaCount(2);
		assertFalse(criterion.isFulfilled(""));
		assertFalse(criterion.isFulfilled("foBA"));
		assertFalse(criterion.isFulfilled("foo"));
		assertFalse(criterion.isFulfilled("BAR"));
		assertTrue(criterion.isFulfilled("fooBAR"));
		assertTrue(criterion.isFulfilled("fOoBaR"));
	}
}
