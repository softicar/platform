package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.mapper.AbstractEmfPermissionTest;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfPermissionTest extends AbstractEmfPermissionTest {

	private static final IDisplayString PERMISSION_NAME = IDisplayString.create("somePermission");

	private final PermissionEntity entity;
	private final EmfTestUser operator;

	public EmfPermissionTest() {

		this.entity = new PermissionEntity();
		this.operator = EmfTestUser.insert("operator", "user");
		this.entity.addOperator(operator);

		// suppress lower-level logs in this test
		LogLevel.ERROR.set();
	}

	@Test
	public void testWithPositiveResult() {

		assertTrue(new EmfPermission<>(PERMISSION_NAME, IEmfPermissionPredicate.always()).test(entity, operator));
	}

	@Test
	public void testWithNegativeResult() {

		assertFalse(new EmfPermission<>(PERMISSION_NAME, IEmfPermissionPredicate.never()).test(entity, operator));
	}

	@Test
	public void testAssumesNegativeResultOnException() {

		assertFalse(new EmfPermission<>(PERMISSION_NAME, new ThrowingPredicate<>()).test(entity, operator));
	}

	private class ThrowingPredicate<R> implements IEmfPermissionPredicate<R> {

		@Override
		public boolean test(R tableRow, IBasicUser user) {

			throw new RuntimeException();
		}
	}
}
