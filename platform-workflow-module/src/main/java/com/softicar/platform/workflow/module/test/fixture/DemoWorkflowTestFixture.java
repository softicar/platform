package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.workflow.module.demo.configuration.WorkflowDemoConfiguration;

public class DemoWorkflowTestFixture implements ITestFixture, WorkflowModuleTestFixtureMethods {

	@Override
	public void apply() {

		new WorkflowDemoConfiguration(use(WorkflowModuleTestFixture.class).getModuleInstance()).createAndSaveAll();
	}
}
