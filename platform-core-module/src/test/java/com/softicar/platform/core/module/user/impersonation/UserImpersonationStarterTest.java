package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import org.junit.Test;

public class UserImpersonationStarterTest extends AbstractUserImpersonationTest {

	@Test
	public void test() {

		insertAdministrationPermission(CurrentUser.get());

		AGUserImpersonationState state = new UserImpersonationStarter(impersonatedUser, RATIONALE).start();

		assertSame(state, assertOne(AGUserImpersonationState.TABLE.loadAll()));
		assertEquals(CurrentUser.get(), state.getSourceUserID());
		assertEquals(impersonatedUser, state.getTargetUser());
		assertEquals(RATIONALE, state.getReason());
		assertNotNull(state.getStartedAt());
		assertNull(state.getFinishedAt());

		assertOne(AGBufferedEmail.TABLE.loadAll());
	}

	@Test(expected = SofticarUserException.class)
	public void testWithCurrentUserIsNotSuperUser() {

		new UserImpersonationStarter(impersonatedUser, RATIONALE).start();
	}

	@Test(expected = SofticarUserException.class)
	public void testWithImpersonatedUserIsSuperUser() {

		insertAdministrationPermission(CurrentUser.get());
		insertAdministrationPermission(impersonatedUser);

		new UserImpersonationStarter(impersonatedUser, RATIONALE).start();
	}

	private void insertAdministrationPermission(AGUser user) {

		insertPermissionAssignment(user, CorePermissions.ADMINISTRATION, AGCoreModuleInstance.getInstance());
	}
}
