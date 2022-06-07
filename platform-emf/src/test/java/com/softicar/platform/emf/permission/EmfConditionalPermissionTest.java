package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.emf.mapper.AbstractEmfPermissionTest;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfConditionalPermissionTest extends AbstractEmfPermissionTest {

	private final EmfPredicate<PermissionEntity> truePredicate;
	private final EmfPredicate<PermissionEntity> falsePredicate;
	private final EmfPredicate<PermissionEntity> throwingPredicate;
	private final PermissionEntity entity;
	private final EmfTestUser operator;

	public EmfConditionalPermissionTest() {

		this.truePredicate = new EmfPredicate<>(IDisplayString.create("True"), entity -> true);
		this.falsePredicate = new EmfPredicate<>(IDisplayString.create("False"), entity -> false);
		this.throwingPredicate = new EmfPredicate<>(IDisplayString.create("Throwing"), entity -> {
			throw new RuntimeException();
		});

		this.entity = new PermissionEntity();
		this.operator = EmfTestUser.insert("operator", "user");
		this.entity.addOperator(operator);

		// suppress lower-level logs in this test
		LogLevel.ERROR.set();
	}

	@Test
	public void testWithPositiveResult() {

		assertTrue(new EmfConditionalPermission<>(OPERATOR, truePredicate).test(entity, operator));
	}

	@Test
	public void testWithPositiveResultAndNullEntity() {

		assertFalse(new EmfConditionalPermission<>(OPERATOR, truePredicate).test(null, operator));
	}

	@Test
	public void testWithNegativeResult() {

		assertFalse(new EmfConditionalPermission<>(OPERATOR, falsePredicate).test(entity, operator));
	}

	@Test
	public void testAssumesNegativeResultOnException() {

		assertFalse(new EmfConditionalPermission<>(OPERATOR, throwingPredicate).test(entity, operator));
	}
}
