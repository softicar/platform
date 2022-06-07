package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import java.util.HashMap;
import java.util.Map;

public class CreateNewWorkflowVersionAction implements IEmfManagementAction<AGWorkflowVersion> {

	@Override
	public IEmfPredicate<AGWorkflowVersion> getPrecondition() {

		return WorkflowPredicates.WORKFLOW_VERSION_FINALIZED;
	}

	@Override
	public IEmfPermission<AGWorkflowVersion> getRequiredPermission() {

		return WorkflowPermissions.ADMINISTRATOR
			.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance()));
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.COPY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.COPY_WORKFLOW_VERSION;
	}

	@Override
	public void handleClick(AGWorkflowVersion oldWorkflowVersion) {

		try (var transaction = new DbTransaction()) {
			var newWorkflowVersion = oldWorkflowVersion//
				.copy()
				.setDraft(true)
				.setRootNode(null)
				.save();

			// Copy Workflow Nodes + Child Tables
			var oldNodeToNewNodeMap = createOldToNewNodeMap(//
				oldWorkflowVersion,
				newWorkflowVersion);

			for (var oldNode: oldNodeToNewNodeMap.keySet()) {
				var newNode = oldNodeToNewNodeMap.get(oldNode);

				// Copy Workflow Node Actions+Action Permissions
				for (var oldAction: oldNode.getAllActiveWorkflowNodeActions()) {
					final AGWorkflowNodeAction newAction = oldAction.copy().setWorkflowNode(newNode).save();
					for (AGWorkflowNodeActionPermission oldActionPermission: oldAction.getAllActiveWorkflowNodeActionPermissions()) {
						oldActionPermission.copy().setWorkflowNodeAction(newAction).save();
					}
				}

				// Copy Workflow Node Preconditions
				for (var oldPrecondition: oldNode.getAllActiveWorkflowNodePreconditions()) {
					oldPrecondition.copy().setWorkflowNode(newNode).save();
				}
			}

			// Copy Workflow Transitions + Child Tables

			for (var oldTransition: oldWorkflowVersion.getAllActiveTransitions()) {
				var newTransition = oldTransition.copy();
				newTransition.setWorkflowVersion(newWorkflowVersion);
				newTransition.setSourceNode(oldNodeToNewNodeMap.get(oldTransition.getSourceNode()));
				newTransition.setTargetNode(oldNodeToNewNodeMap.get(oldTransition.getTargetNode()));
				newTransition.save();

				// Copy Workflow Transition Permissions
				for (var oldPermission: oldTransition.getAllActiveWorkflowTransitionPermissions()) {
					oldPermission.copy().setTransition(newTransition).save();
				}
			}

			newWorkflowVersion//
				.setRootNode(oldNodeToNewNodeMap.get(oldWorkflowVersion.getRootNode()))
				.save();

			transaction.commit();
		}

		CurrentDomDocument.get().getRefreshBus().setAllChanged();

	}

	private Map<AGWorkflowNode, AGWorkflowNode> createOldToNewNodeMap(AGWorkflowVersion oldVersion, AGWorkflowVersion newVersion) {

		HashMap<AGWorkflowNode, AGWorkflowNode> map = new HashMap<>();
		oldVersion.getAllActiveWorkflowNodes().forEach(it -> {
			map.put(it, it.copy().setWorkflowVersion(newVersion).save());
		});

		return map;
	}
}
