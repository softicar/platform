package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import com.softicar.platform.workflow.module.workflow.node.action.permission.WorkflowNodeActionPermission;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public interface IWorkflowAction<R extends IWorkflowableObject<R>> extends IEmfAction<R>, ISourceCodeReferencePoint {

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
	default IEmfPermission<R> getRequiredPermission() {

		return new WorkflowNodeActionPermission<>(getNodesToPermissionsMap());
	}

	private Map<AGWorkflowNode, Collection<AGWorkflowNodeActionPermission>> getNodesToPermissionsMap() {

		Map<AGWorkflowNode, Collection<AGWorkflowNodeActionPermission>> nodesToPermissionsMap = new TreeMap<>();
		AGWorkflowNodeAction
			.createSelect()
			.where(AGWorkflowNodeAction.ACTION.equal(AGUuid.getOrCreate(getAnnotatedUuid())))
			.where(AGWorkflowNodeAction.ACTIVE)
			.forEach(action -> nodesToPermissionsMap.put(action.getWorkflowNode(), action.getAllActiveWorkflowNodeActionPermissions()));

		return nodesToPermissionsMap;
	}
}
