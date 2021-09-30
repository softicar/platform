package com.softicar.platform.workflow.module.test;

import com.softicar.platform.workflow.module.AbstractWorkflowTest;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public abstract class AbstractTestObjectWorkflowTest extends AbstractWorkflowTest {

	protected final AGWorkflow workflow;
	protected final AGWorkflowVersion workflowVersion;
	protected final AGWorkflowNode rootNode;

	public AbstractTestObjectWorkflowTest() {

		this.workflow = insertWorkflow(moduleInstance, "Test Workflow", WorkflowTestObjectTableReferencePoint.class);
		this.workflowVersion = insertWorkflowVersion(workflow, false);
		this.rootNode = insertWorkflowNode(workflowVersion, "Root Node");

		workflow.setCurrentVersion(workflowVersion).save();
		workflowVersion.setRootNode(rootNode).save();
	}
}
