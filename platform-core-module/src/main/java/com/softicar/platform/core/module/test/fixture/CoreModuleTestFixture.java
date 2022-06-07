package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;

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

		AGCoreModuleInstance//
			.getInstance()
			.setTestSystem(true)
			.setDefaultLocalization(insertLocalizationPresetGermany())
			.save();

		this.viewUser = insertUser("View", "User")//
			.setEmailAddress("view.user@example.com")
			.setAutomaticallyCollapseFolders(false)
			.save();

		this.normalUser = insertUser("Normal", "User")//
			.setEmailAddress("normal.user@example.com")
			.setAutomaticallyCollapseFolders(false)
			.save();

		this.adminUser = insertUser("Admin", "User")//
			.setEmailAddress("admin.user@example.com")
			.setAutomaticallyCollapseFolders(false)
			.save();

		insertPassword(viewUser, "test");
		insertPassword(normalUser, "test");
		insertPassword(adminUser, "test");

		insertPermissionAssignment(adminUser, CorePermissions.ACCESS_MANAGEMENT, CoreModule.class);
		insertPermissionAssignment(adminUser, CorePermissions.SUPER_USER, CoreModule.class);
		insertPermissionAssignment(adminUser, CorePermissions.SYSTEM_ADMINISTRATION, CoreModule.class);

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

	public <I extends IStandardModuleInstance<I>> void insertStandardPermissionAssignments(I moduleInstance) {

		insertPermissionAssignment(getViewUser(), EmfDefaultModulePermissions.getModuleViewer(), moduleInstance);
		insertPermissionAssignment(getNormalUser(), EmfDefaultModulePermissions.getModuleOperator(), moduleInstance);
		insertPermissionAssignment(getAdminUser(), EmfDefaultModulePermissions.getModuleAdministator(), moduleInstance);
	}
}
