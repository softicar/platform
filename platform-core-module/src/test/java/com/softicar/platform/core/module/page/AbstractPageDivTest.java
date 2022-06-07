package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.UserPasswordUpdater;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public abstract class AbstractPageDivTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	protected final AGUser testUser;

	public AbstractPageDivTest() {

		this.testUser = insertTestUser();
		new UserPasswordUpdater(testUser, "").updatePasswordInDatabase();

		AGLocalization localization = insertLocalizationPresetGermany();
		insertCoreModuleInstance(localization);

		CurrentUser.set(testUser);
		insertPermissionAssignment(testUser, CorePermissions.SUPER_USER, CoreModule.class);
	}
}
