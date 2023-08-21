package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.workflow.module.standard.configuration.WorkflowIconStandardConfiguration;

public class WorkflowStandardIconsTestFixture implements ITestFixture, WorkflowModuleTestFixtureMethods {

	@Override
	public void apply() {

		new WorkflowIconStandardConfiguration(use(WorkflowModuleTestFixture.class).getModuleInstance()).createAndSaveAll();
	}
}
