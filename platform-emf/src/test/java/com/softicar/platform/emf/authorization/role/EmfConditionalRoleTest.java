package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.emf.authorization.AbstractEmfRoleTest;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfConditionalRoleTest extends AbstractEmfRoleTest {

	private final EmfPredicate<RoleEntity> truePredicate;
	private final EmfPredicate<RoleEntity> falsePredicate;
	private final EmfPredicate<RoleEntity> throwingPredicate;
	private final RoleEntity entity;
	private final EmfTestUser operator;

	public EmfConditionalRoleTest() {

		this.truePredicate = new EmfPredicate<>(IDisplayString.create("True"), entity -> true);
		this.falsePredicate = new EmfPredicate<>(IDisplayString.create("False"), entity -> false);
		this.throwingPredicate = new EmfPredicate<>(IDisplayString.create("Throwing"), entity -> {
			throw new RuntimeException();
		});

		this.entity = new RoleEntity();
		this.operator = EmfTestUser.insert("operator", "user");
		this.entity.addOperator(operator);

		// suppress lower-level logs in this test
		LogLevel.ERROR.set();
	}

	@Test
	public void testWithPositiveResult() {

		assertTrue(new EmfConditionalRole<>(OPERATOR, truePredicate).test(entity, operator));
	}

	@Test
	public void testWithPositiveResultAndNullEntity() {

		assertFalse(new EmfConditionalRole<>(OPERATOR, truePredicate).test(null, operator));
	}

	@Test
	public void testWithNegativeResult() {

		assertFalse(new EmfConditionalRole<>(OPERATOR, falsePredicate).test(entity, operator));
	}

	@Test
	public void testAssumesNegativeResultOnException() {

		assertFalse(new EmfConditionalRole<>(OPERATOR, throwingPredicate).test(entity, operator));
	}
}
