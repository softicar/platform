package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixture;
import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.demo.configuration.WorkflowDemoConfiguration;
import com.softicar.platform.workflow.module.standard.configuration.WorkflowIconStandardConfiguration;

public final class WorkflowModuleTestFixture implements WorkflowModuleTestFixtureMethods, ITestFixture {

	private AGWorkflowModuleInstance moduleInstance;

	public AGWorkflowModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = insertWorkflowModuleInstance();
		use(CoreModuleTestFixture.class).insertStandardPermissionAssignments(moduleInstance);
		new WorkflowIconStandardConfiguration(moduleInstance).createAndSaveAll();
		new WorkflowDemoConfiguration(moduleInstance, use(CoreModuleTestFixture.class)).createAndSaveAll();
	}
}
