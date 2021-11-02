package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.role.AGWorkflowNodeActionRole;
import com.softicar.platform.workflow.module.workflow.node.action.role.WorkflowNodeActionRole;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public interface IWorkflowAction<R extends IWorkflowableObject<R>> extends IEmfAction<R>, IEmfSourceCodeReferencePoint {

	@Override
	default IDisplayString toDisplay() {

		return getTitle();
	}

	public IEmfPredicate<R> getAdditionalPrecondition();

	@Override
	default IEmfPredicate<R> getPrecondition() {

		Set<AGWorkflowNode> validWorkflowNodes = AGWorkflowNodeAction
			.createSelect()
			.where(AGWorkflowNodeAction.ACTION.equal(AGUuid.getOrCreate(getAnnotatedUuid())))
			.where(AGWorkflowNodeAction.ACTIVE)
			.stream()
			.map(workflowNodeAction -> workflowNodeAction.getWorkflowNode())
			.collect(Collectors.toSet());

		return getAdditionalPrecondition().and(new WorkflowNodeActionPredicate<>(validWorkflowNodes));
	}

	@Override
	default IEmfRole<R> getAuthorizedRole() {

		return new WorkflowNodeActionRole<>(getNodesToRolesMap());
	}

	private Map<AGWorkflowNode, Collection<AGWorkflowNodeActionRole>> getNodesToRolesMap() {

		Map<AGWorkflowNode, Collection<AGWorkflowNodeActionRole>> nodesToRolesMap = new TreeMap<>();
		AGWorkflowNodeAction
			.createSelect()
			.where(AGWorkflowNodeAction.ACTION.equal(AGUuid.getOrCreate(getAnnotatedUuid())))
			.where(AGWorkflowNodeAction.ACTIVE)
			.forEach(action -> nodesToRolesMap.put(action.getWorkflowNode(), action.getAllActiveWorkflowNodeActionRoles()));

		return nodesToRolesMap;
	}
}
