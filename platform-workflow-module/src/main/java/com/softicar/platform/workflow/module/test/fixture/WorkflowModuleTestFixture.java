package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.demo.configuration.WorkflowDemoConfiguration;
import com.softicar.platform.workflow.module.standard.configuration.WorkflowIconStandardConfiguration;

public class WorkflowModuleTestFixture implements WorkflowModuleTestFixtureMethods, IStandardModuleTestFixture<AGWorkflowModuleInstance> {

	private AGWorkflowModuleInstance workflowModuleInstance;
	private final TestFixtureRegistry registry;

	public WorkflowModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGWorkflowModuleInstance getInstance() {

		return workflowModuleInstance;
	}

	@Override
	public IStandardModuleTestFixture<AGWorkflowModuleInstance> apply() {

		this.workflowModuleInstance = insertWorkflowModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(workflowModuleInstance);
		new WorkflowIconStandardConfiguration(workflowModuleInstance).createAndSaveAll();
		new WorkflowDemoConfiguration(workflowModuleInstance, registry.getCoreModuleTestFixture()).createAndSaveAll();
		return this;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGWorkflowModuleInstance.TABLE;
	}
}
