package com.softicar.platform.workflow.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import java.util.Collection;

public class AGWorkflowModuleInstance extends AGWorkflowModuleInstanceGenerated implements IModuleInstance<AGWorkflowModuleInstance> {

	public static Collection<AGWorkflowModuleInstance> loadAllActive() {

		return AGWorkflowModuleInstance.TABLE//
			.createSelect()
			.join(AGWorkflowModuleInstance.BASE)
			.where(AGModuleInstanceBase.ACTIVE)
			.orderBy(AGModuleInstanceBase.ID)
			.list();
	}

	public static Collection<AGWorkflowModuleInstance> loadAllActiveAndVisible(AGUser user) {

		return loadAllActive()//
			.stream()
			.filter(moduleInstance -> EmfDefaultModulePermissions.<AGWorkflowModuleInstance> getModuleView().test(moduleInstance, user))
			.toList();
	}
}
