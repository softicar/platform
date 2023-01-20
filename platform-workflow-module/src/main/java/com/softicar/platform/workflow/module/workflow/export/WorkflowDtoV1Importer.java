package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.common.container.map.instance.ClassInstanceListMap;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Optional;
import java.util.UUID;

public class WorkflowDtoV1Importer {

	private final AGWorkflowModuleInstance moduleInstance;
	private final WorkflowDtoV1 workflowDto;
	private final ClassInstanceListMap tableRows;
	private AGWorkflow workflow;

	public WorkflowDtoV1Importer(AGWorkflowModuleInstance moduleInstance, WorkflowDtoV1 workflowDto) {

		this(moduleInstance, null, workflowDto);
	}

	public WorkflowDtoV1Importer(AGWorkflow workflow, WorkflowDtoV1 workflowDto) {

		this(workflow.getModuleInstance(), workflow, workflowDto);
	}

	private WorkflowDtoV1Importer(AGWorkflowModuleInstance moduleInstance, AGWorkflow workflow, WorkflowDtoV1 workflowDto) {

		this.moduleInstance = moduleInstance;
		this.workflowDto = workflowDto;
		this.tableRows = new ClassInstanceListMap();
		this.workflow = workflow;
	}

	public AGWorkflowVersion importWorkflow() {

		if (workflow == null) {
			this.workflow = getOrCreateWorkflow();
		}
		var workflowVersion = addTableRow(new AGWorkflowVersion())//
			.setDraft(true)
			.setWorkflow(workflow)
			.setRootNode(null);
		workflowDto.nodes.forEach(node -> addWorkflowNode(workflowVersion, node));
		workflowDto.transitions.forEach(transition -> addWorkflowTransition(workflowVersion, transition));

		try (var transaction = new DbTransaction()) {
			saveTableRows(AGWorkflow.TABLE);
			saveTableRows(AGWorkflowVersion.TABLE);
			saveTableRows(AGWorkflowNode.TABLE);
			saveTableRows(AGWorkflowNodeAction.TABLE);
			saveTableRows(AGWorkflowNodeActionPermission.TABLE);
			saveTableRows(AGWorkflowNodePrecondition.TABLE);
			saveTableRows(AGWorkflowTransition.TABLE);
			saveTableRows(AGWorkflowTransitionPermission.TABLE);
			Optional//
				.ofNullable(workflowDto.rootNode)
				.ifPresent(rootNode -> workflowVersion.setRootNode(getWorkflowNode(rootNode)).save());
			transaction.commit();
		}

		return workflowVersion;
	}

	private void addWorkflowNode(AGWorkflowVersion workflowVersion, WorkflowDtoV1.Node node) {

		var workflowNode = addTableRow(new AGWorkflowNode())//
			.setActive(true)
			.setName(node.name)
			.setWorkflowVersion(workflowVersion)
			.setXCoordinate(node.x)
			.setYCoordinate(node.y);
		node.actions.forEach(action -> addWorkflowNodeActionAndPermissions(workflowNode, action));
		node.preconditions.forEach(precondition -> addWorkflowNodePrecondition(workflowNode, precondition));
	}

	private void addWorkflowNodeActionAndPermissions(AGWorkflowNode workflowNode, WorkflowDtoV1.NodeAction nodeAction) {

		var workflowNodeAction = addTableRow(new AGWorkflowNodeAction())//
			.setAction(getOrCreateUuid(nodeAction.action))
			.setActive(true)
			.setWorkflowNode(workflowNode);
		nodeAction.permissions.forEach(permission -> addWorkflowNodeActionPermission(workflowNodeAction, permission));
	}

	private void addWorkflowNodeActionPermission(AGWorkflowNodeAction workflowNodeAction, String permission) {

		addTableRow(new AGWorkflowNodeActionPermission())//
			.setActive(true)
			.setPermissionUuid(getOrCreateUuid(permission))
			.setWorkflowNodeAction(workflowNodeAction);
	}

	private void addWorkflowNodePrecondition(AGWorkflowNode workflowNode, String precondition) {

		addTableRow(new AGWorkflowNodePrecondition())//
			.setActive(true)
			.setFunction(getOrCreateUuid(precondition))
			.setWorkflowNode(workflowNode);
	}

	private void addWorkflowTransition(AGWorkflowVersion workflowVersion, WorkflowDtoV1.Transition transition) {

		var workflowTransition = addTableRow(new AGWorkflowTransition())//
			.setActive(true)
			.setAutoTransition(transition.autoTransition)
			.setHtmlColor(transition.htmlColor)
			.setName(transition.name)
			.setNotify(transition.notify)
			.setRequiredVotes(transition.requiredVotes)
			.setSideEffect(transition.sideEffect.isBlank()? null : getOrCreateUuid(transition.sideEffect))
			.setSourceNode(getWorkflowNode(transition.sourceNode))
			.setTargetNode(getWorkflowNode(transition.targetNode))
			.setTransitionIcon(getWorkflowIcon(workflowVersion, transition))
			.setWorkflowVersion(workflowVersion);
		transition.permissions.forEach(permission -> addWorkflowTransitionPermission(workflowTransition, permission));
	}

	private void addWorkflowTransitionPermission(AGWorkflowTransition workflowTransition, String permission) {

		addTableRow(new AGWorkflowTransitionPermission())//
			.setActive(true)
			.setPermission(getOrCreateUuid(permission))
			.setTransition(workflowTransition);
	}

	private AGUuid getOrCreateUuid(String uuid) {

		return AGUuid.getOrCreate(UUID.fromString(uuid));
	}

	private AGWorkflowNode getWorkflowNode(int index) {

		return tableRows.getInstances(AGWorkflowNode.class).get(index);
	}

	private AGWorkflowIcon getWorkflowIcon(AGWorkflowVersion workflowVersion, WorkflowDtoV1.Transition transition) {

		return AGWorkflowIcon.getByName(workflowVersion.getWorkflow().getModuleInstance(), transition.icon).orElse(null);
	}

	private <R extends IDbTableRow<R, ?>> R addTableRow(R tableRow) {

		tableRows.add(tableRow);
		return tableRow;
	}

	private <R extends IDbTableRow<R, ?>> void saveTableRows(IDbTable<R, ?> table) {

		table.saveAll(tableRows.getInstances(table.getValueClass()));
	}

	private AGWorkflow getOrCreateWorkflow() {

		var entityTableUuid = AGUuid.getOrCreate(UUID.fromString(workflowDto.entityType));

		var workflow = AGWorkflow.TABLE//
			.createSelect()
			.where(AGWorkflow.MODULE_INSTANCE.isEqual(moduleInstance))
			.where(AGWorkflow.NAME.isEqual(workflowDto.name))
			.where(AGWorkflow.ENTITY_TABLE.isEqual(entityTableUuid))
			.getOneAsOptional()
			.orElseGet(
				() -> new AGWorkflow()//
					.setActive(true)
					.setEntityTable(entityTableUuid)
					.setModuleInstance(moduleInstance)
					.setName(workflowDto.name));

		return addTableRow(workflow.setActive(true));
	}
}
