package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.AbstractEmfRoleTest;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfRoleTest extends AbstractEmfRoleTest {

	private static final IDisplayString ROLE_NAME = IDisplayString.create("someRole");

	private final RoleEntity entity;
	private final EmfTestUser operator;

	public EmfRoleTest() {

		this.entity = new RoleEntity();
		this.operator = EmfTestUser.insert("operator", "user");
		this.entity.addOperator(operator);

		// suppress lower-level logs in this test
		LogLevel.ERROR.set();
	}

	@Test
	public void testWithPositiveResult() {

		assertTrue(new EmfRole<>(ROLE_NAME, IEmfRolePredicate.always()).test(entity, operator));
	}

	@Test
	public void testWithNegativeResult() {

		assertFalse(new EmfRole<>(ROLE_NAME, IEmfRolePredicate.never()).test(entity, operator));
	}

	@Test
	public void testAssumesNegativeResultOnException() {

		assertFalse(new EmfRole<>(ROLE_NAME, new ThrowingPredicate<>()).test(entity, operator));
	}

	private class ThrowingPredicate<R> implements IEmfRolePredicate<R> {

		@Override
		public boolean test(R tableRow, IBasicUser user) {

			throw new RuntimeException();
		}
	}
}
