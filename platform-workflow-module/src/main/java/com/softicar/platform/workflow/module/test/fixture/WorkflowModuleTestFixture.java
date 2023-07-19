package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;

public final class WorkflowModuleTestFixture implements WorkflowModuleTestFixtureMethods, ITestFixture {

	private AGWorkflowModuleInstance moduleInstance;

	public AGWorkflowModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = insertWorkflowModuleInstance();

		insertStandardPermissionAssignments(moduleInstance);
	}
}
