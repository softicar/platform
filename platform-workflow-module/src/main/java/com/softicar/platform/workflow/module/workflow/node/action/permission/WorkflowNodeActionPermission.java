package com.softicar.platform.workflow.module.workflow.node.action.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.permission.EmfAnyPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class WorkflowNodeActionPermission<R extends IWorkflowableObject<R>> implements IEmfPermission<R> {

	private final Map<AGWorkflowNode, Collection<AGWorkflowNodeActionPermission>> nodeToPermissionsMap;

	public WorkflowNodeActionPermission(Map<AGWorkflowNode, Collection<AGWorkflowNodeActionPermission>> nodeToPermissionsMap) {

		this.nodeToPermissionsMap = nodeToPermissionsMap;
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSION;
	}

	@Override
	public boolean test(R workflowableObject, IBasicUser user) {

		return createPermissionForSpecificWorkflowNode(workflowableObject.getWorkflowItem().getWorkflowNode()).test(workflowableObject, user);
	}

	public EmfAnyPermission<R> createPermissionForSpecificWorkflowNode(AGWorkflowNode node) {

		return createAnyPermission(nodeToPermissionsMap.getOrDefault(node, Collections.emptyList()));
	}

	private EmfAnyPermission<R> createAnyPermission(Collection<AGWorkflowNodeActionPermission> workflowActionPermissions) {

		Collection<IEmfPermission<R>> permissions = new HashSet<>();
		for (AGWorkflowNodeActionPermission workflowActionPermission: workflowActionPermissions) {
			permissions.add(workflowActionPermission.getStaticPermissionOrThrow());
		}
		return new EmfAnyPermission<>(permissions);
	}

}
