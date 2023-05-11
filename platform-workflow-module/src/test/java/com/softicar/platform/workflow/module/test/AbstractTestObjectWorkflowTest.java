package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public abstract class AbstractTestObjectWorkflowTest extends AbstractTestWorkflowTest<WorkflowTestObject, WorkflowTestObjectPermissionAssignment> {

	public AbstractTestObjectWorkflowTest() {

		super(WorkflowTestObjectTableReferencePoint.class);
	}

	@Override
	protected WorkflowTestObject createEntity(String name, AGWorkflowItem item) {

		return new WorkflowTestObject()//
			.setName(name)
			.setWorkflowItem(item);
	}

	@Override
	protected WorkflowTestObjectPermissionAssignment createPermissionAssignment(IBasicUser user, WorkflowTestObject testEntity, String permission) {

		return new WorkflowTestObjectPermissionAssignment()//
			.setPermission(permission)
			.setObject(testEntity)
			.setUser(AGUser.get(user));
	}
}
