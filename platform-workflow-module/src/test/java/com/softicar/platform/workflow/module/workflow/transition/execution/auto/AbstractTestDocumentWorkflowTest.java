package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestWorkflowTest;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public abstract class AbstractTestDocumentWorkflowTest extends AbstractTestWorkflowTest<WorkflowTestDocument, WorkflowTestDocumentPermissionAssignment> {

	public AbstractTestDocumentWorkflowTest() {

		super(WorkflowTestDocumentTableReferencePoint.class);
	}

	@Override
	protected WorkflowTestDocument createEntity(String name, AGWorkflowItem item) {

		return new WorkflowTestDocument()//
			.setName(name)
			.setWorkflowItem(item);
	}

	@Override
	protected WorkflowTestDocumentPermissionAssignment createPermissionAssignment(IBasicUser user, WorkflowTestDocument testEntity, String permission) {

		return new WorkflowTestDocumentPermissionAssignment()//
			.setPermission(permission)
			.setDocument(testEntity)
			.setUser(AGUser.get(user));
	}
}
