package com.softicar.platform.demo.core.module;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.module.instance.actions.ModuleInstanceInitializationAction;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

public class DemoCoreModuleInstanceCreationTest extends AbstractCoreTest implements IEmfTestEngineMethods {

	public DemoCoreModuleInstanceCreationTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGModuleInstance.TABLE, AGCoreModuleInstance.getInstance()).build());
	}

	@Test
	public void testCreation() {

		insertPermissionAssignment(CurrentUser.get(), CorePermissions.ACCESS_MANAGEMENT, CoreModule.class);
		insertPermissionAssignment(CurrentUser.get(), CorePermissions.SUPER_USER, CoreModule.class);

		findManagementDiv(AGModuleInstance.TABLE).clickCreateButton();

		EmfFormPopupTester popup = findFormPopup(AGModuleInstance.class);
		popup.setInputValue(AGModuleInstance.MODULE_UUID, "Demo");
		popup.clickSaveButton();
		popup.clickActionButton(ModuleInstanceInitializationAction.class);

		EmfFormPopupTester demoPopup = findFormPopup(AGDemoCoreModuleInstance.class);
		demoPopup.setInputValue(AGDemoCoreModuleInstance.TITLE, "FooBar");
		demoPopup.clickSaveButton();

		assertEquals("Core Module Instance|FooBar", findEmfDataTable(AGModuleInstance.TABLE).getTextInCells(AGModuleInstance.TITLE_FIELD));
	}
}
