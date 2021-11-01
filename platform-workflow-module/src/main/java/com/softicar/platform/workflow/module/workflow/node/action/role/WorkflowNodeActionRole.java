package com.softicar.platform.workflow.module.workflow.node.action.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class WorkflowNodeActionRole<R extends IWorkflowableObject<R>> implements IEmfRole<R> {

	private final Map<AGWorkflowNode, Collection<AGWorkflowNodeActionRole>> nodeToRolesMap;

	public WorkflowNodeActionRole(Map<AGWorkflowNode, Collection<AGWorkflowNodeActionRole>> nodeToRolesMap) {

		this.nodeToRolesMap = nodeToRolesMap;
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_NODE_ACTION_ROLE;
	}

	@Override
	public boolean test(R workflowableObject, IBasicUser user) {

		return createRoleForSpecificWorkflowNode(workflowableObject.getWorkflowItem().getWorkflowNode()).test(workflowableObject, user);
	}

	public EmfAnyRole<R> createRoleForSpecificWorkflowNode(AGWorkflowNode node) {

		return createAnyRole(nodeToRolesMap.getOrDefault(node, Collections.emptyList()));
	}

	private EmfAnyRole<R> createAnyRole(Collection<AGWorkflowNodeActionRole> workflowActionRoles) {

		Collection<IEmfRole<R>> roles = new HashSet<>();
		for (AGWorkflowNodeActionRole workflowActionRole: workflowActionRoles) {
			roles.add(workflowActionRole.getStaticRoleOrThrow());
		}
		return new EmfAnyRole<>(roles);
	}

}
