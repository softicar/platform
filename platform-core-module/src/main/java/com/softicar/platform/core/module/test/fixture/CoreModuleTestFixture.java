package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;

/**
 * Basic test fixture for the {@link CoreModule}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public final class CoreModuleTestFixture implements ITestFixture, CoreModuleTestFixtureMethods {

	private AGCoreModuleInstance moduleInstance;
	private AGUser viewUser;
	private AGUser normalUser;
	private AGUser adminUser;

	public CoreModuleTestFixture() {

		this.moduleInstance = null;
		this.viewUser = null;
		this.normalUser = null;
		this.adminUser = null;
	}

	public AGCoreModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = AGCoreModuleInstance//
			.getInstance()
			.setTestSystem(true)
			.setDefaultLocalization(insertLocalizationPresetGermany())
			.setEmailServer(insertDummyServer())
			.save();

		this.viewUser = insertUser("View", "User")//
			.setEmailAddress("view.user@example.com")
			.save();

		this.normalUser = insertUser("Normal", "User")//
			.setEmailAddress("normal.user@example.com")
			.save();

		this.adminUser = insertUser("Admin", "User")//
			.setEmailAddress("admin.user@example.com")
			.save();

		insertPassword(viewUser, "test");
		insertPassword(normalUser, "test");
		insertPassword(adminUser, "test");

		insertPermissionAssignment(adminUser, CorePermissions.OPERATION, AGCoreModuleInstance.getInstance());
		insertPermissionAssignment(adminUser, CorePermissions.ADMINISTRATION, AGCoreModuleInstance.getInstance());

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

	public <I extends IModuleInstance<I>> void insertStandardPermissionAssignments(I moduleInstance) {

		insertPermissionAssignment(getViewUser(), EmfDefaultModulePermissions.getModuleView(), moduleInstance);
		insertPermissionAssignment(getNormalUser(), EmfDefaultModulePermissions.getModuleOperation(), moduleInstance);
		insertPermissionAssignment(getAdminUser(), EmfDefaultModulePermissions.getModuleAdministration(), moduleInstance);
	}

	public CoreModuleTestFixture insertUsers(int count) {

		for (int i = 0; i < count; i++) {
			insertUser("user#" + i);
		}
		return this;
	}
}
