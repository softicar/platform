package com.softicar.platform.demo.core.module;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.actions.ModuleInstanceInitializationAction;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

public class DemoCoreModuleInstanceCreationTest extends AbstractCoreTest implements IEmfTestEngineMethods {

	public DemoCoreModuleInstanceCreationTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGModuleInstanceBase.TABLE, AGCoreModuleInstance.getInstance()).build());
	}

	@Test
	public void testCreation() {

		insertPermissionAssignment(CurrentUser.get(), CorePermissions.ACCESS_MANAGEMENT);
		insertPermissionAssignment(CurrentUser.get(), CorePermissions.SUPER_USER);

		findManagementDiv(AGModuleInstanceBase.TABLE).clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGModuleInstanceBase.class);
		popup.setInputValue(AGModuleInstanceBase.MODULE_UUID, "Demo");
		popup.clickSaveButton();
		popup.clickActionButton(ModuleInstanceInitializationAction.class);

		EmfFormPopupTester demoPopup = findFormPopup(AGDemoCoreModuleInstance.class);
		demoPopup.setInputValue(AGDemoCoreModuleInstance.TITLE, "FooBar");
		demoPopup.clickSaveButton();

		assertEquals("Core Module Instance|FooBar", findEmfDataTable(AGModuleInstanceBase.TABLE).getTextInCells(AGModuleInstanceBase.TITLE_FIELD));
	}
}
