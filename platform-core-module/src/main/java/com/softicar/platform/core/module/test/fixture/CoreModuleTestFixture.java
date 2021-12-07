package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.role.EmfDefaultModuleRoles;

/**
 * Basic test fixture for the {@link CoreModule}.
 *
 * @author Oliver Richers
 */
public class CoreModuleTestFixture implements CoreModuleTestFixtureMethods {

	private final AGUser viewUser;
	private final AGUser normalUser;
	private final AGUser adminUser;

	/**
	 * FIXME This pattern is abysmal - constructors shall never have a side
	 * effect on the database!
	 */
	public CoreModuleTestFixture() {

		this.viewUser = insertUser("View", "User")//
			.setEmailAddress("view.user@example.com")
			.save();

		this.normalUser = insertUser("Normal", "User")//
			.setEmailAddress("normal.user@example.com")
			.save();

		this.adminUser = insertUser("Admin", "User")//
			.setEmailAddress("admin.user@example.com")
			.save();

		AGCoreModuleInstance.getInstance().setTestSystem(true).save();

		insertPassword(viewUser, "test");
		insertPassword(normalUser, "test");
		insertPassword(adminUser, "test");

		insertRoleMembership(adminUser, CoreRoles.ACCESS_MANAGER, CoreModule.class);
		insertRoleMembership(adminUser, CoreRoles.SUPER_USER, CoreModule.class);

		new ProgramStandardConfiguration().createAndSaveAll();
	}

	public AGUser getViewUser() {

		return viewUser;
	}

	public AGUser getNormalUser() {

		return normalUser;
	}

	public AGUser getAdminUser() {

		return adminUser;
	}

	public <I extends IStandardModuleInstance<I>> void insertStandardRoleMemberships(I moduleInstance) {

		insertRoleMembership(getViewUser(), EmfDefaultModuleRoles.getModuleViewer(), moduleInstance);
		insertRoleMembership(getNormalUser(), EmfDefaultModuleRoles.getModuleOperator(), moduleInstance);
		insertRoleMembership(getAdminUser(), EmfDefaultModuleRoles.getModuleAdministator(), moduleInstance);
	}
}
