package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import com.softicar.platform.core.module.test.instance.registry.IModuleTestFixture;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.table.IEmfTable;

/**
 * Basic test fixture for the {@link CoreModule}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class CoreModuleTestFixture implements IModuleTestFixture<AGCoreModuleInstance>, CoreModuleTestFixtureMethods {

	private AGCoreModuleInstance instance;
	private AGUser viewUser;
	private AGUser normalUser;
	private AGUser adminUser;

	public CoreModuleTestFixture() {

		this.instance = null;
		this.viewUser = null;
		this.normalUser = null;
		this.adminUser = null;
	}

	@Override
	public AGCoreModuleInstance getInstance() {

		return instance;
	}

	@Override
	public IModuleTestFixture<AGCoreModuleInstance> apply() {

		this.instance = AGCoreModuleInstance//
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

		return this;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGCoreModuleInstance.TABLE;
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
}
