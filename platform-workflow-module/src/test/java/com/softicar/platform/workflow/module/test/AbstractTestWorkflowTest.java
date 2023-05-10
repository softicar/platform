package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.workflow.module.AbstractWorkflowTest;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Objects;

public abstract class AbstractTestWorkflowTest<E extends IEmfEntity<E, ?>, P extends IDbObject<P>> extends AbstractWorkflowTest {

	protected final AGWorkflow workflow;
	protected final AGWorkflowVersion workflowVersion;
	protected final AGWorkflowNode rootNode;

	public AbstractTestWorkflowTest(Class<? extends IWorkflowTableReferencePoint<E>> referencePointClass) {

		this.workflow = insertWorkflow(moduleInstance, "Test Workflow", referencePointClass);
		this.workflowVersion = insertWorkflowVersion(workflow, false);
		this.rootNode = insertWorkflowNode(workflowVersion, "Root Node");

		workflow.setCurrentVersion(workflowVersion).save();
		workflowVersion.setRootNode(rootNode).save();
	}

	protected abstract E createEntity(String name, AGWorkflowItem item);

	protected abstract P createPermissionAssignment(IBasicUser user, E testEntity, String permission);

	public E insertWorkflowTestEntity(String name, AGWorkflowItem item) {

		return createEntity(name, item).save();
	}

	public void insertPermissionA(IBasicUser user, E testEntity) {

		insertPermission(user, testEntity, "A");
	}

	public void insertPermissionB(IBasicUser user, E testEntity) {

		insertPermission(user, testEntity, "B");
	}

	public void insertPermission(IBasicUser user, E testEntity, String permission) {

		createPermissionAssignment(user, testEntity, permission).save();
	}

	public void initializeItemAndObject(IWorkflowableObject<?> object) {

		Objects.requireNonNull(object);

		var item = new AGWorkflowItem() //
			.setWorkflow(workflow)
			.setWorkflowNode(workflow.getCurrentVersion().getRootNode())
			.save();

		object//
			.setWorkflowItem(item)
			.save();
	}
}
