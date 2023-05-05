package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.AbstractWorkflowTest;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public abstract class AbstractTestDocumentWorkflowTest extends AbstractWorkflowTest {

	protected final AGWorkflow workflow;
	protected final AGWorkflowVersion workflowVersion;
	protected final AGWorkflowNode rootNode;

	public AbstractTestDocumentWorkflowTest() {

		this.workflow = insertWorkflow(moduleInstance, "Test Workflow", WorkflowTestDocumentTableReferencePoint.class);
		this.workflowVersion = insertWorkflowVersion(workflow, false);
		this.rootNode = insertWorkflowNode(workflowVersion, "Root Node");

		workflow.setCurrentVersion(workflowVersion).save();
		workflowVersion.setRootNode(rootNode).save();
	}

	public WorkflowTestDocument insertWorkflowTestDocument(String name, AGWorkflowItem item) {

		return new WorkflowTestDocument()//
			.setName(name)
			.setWorkflowItem(item)
			.save();
	}

	public void insertPermissionA(IBasicUser user, WorkflowTestDocument testDocument) {

		insertPermission(user, testDocument, "A");
	}

	public void insertPermissionB(IBasicUser user, WorkflowTestDocument testDocument) {

		insertPermission(user, testDocument, "B");
	}

	public void insertPermission(IBasicUser user, WorkflowTestDocument testDocument, String permission) {

		new WorkflowTestDocumentPermissionAssignment()//
			.setPermission(permission)
			.setDocument(testDocument)
			.setUser(AGUser.get(user))
			.save();
	}
}
