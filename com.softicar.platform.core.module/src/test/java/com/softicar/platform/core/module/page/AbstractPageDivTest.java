package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.UserPasswordUpdater;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public abstract class AbstractPageDivTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private final AGUser testUser;

	public AbstractPageDivTest() {

		this.testUser = insertTestUser();
		new UserPasswordUpdater(testUser, "").updatePasswordInDatabase();
		insertCoreModuleInstance();

		CurrentUser.set(testUser);
		insertRoleMembership(testUser, CoreRoles.SUPER_USER, CoreModule.class);
	}
}
