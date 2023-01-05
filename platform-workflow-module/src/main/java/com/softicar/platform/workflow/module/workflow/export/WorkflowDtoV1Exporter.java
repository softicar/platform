package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class WorkflowDtoV1Exporter {

	private final AGWorkflowVersion workflowVersion;
	private WorkflowDtoV1 workflowDto;
	private IndexHashMap<AGWorkflowNode> nodeIndexMap;
	private Map<AGWorkflowNode, WorkflowDtoV1.Node> nodeDtoMap;
	private Map<AGWorkflowNodeAction, WorkflowDtoV1.NodeAction> actionDtoMap;
	private Map<AGWorkflowTransition, WorkflowDtoV1.Transition> transitionDtoMap;
	private boolean skipNodePositions;

	public WorkflowDtoV1Exporter(AGWorkflow workflow) {

		this(Objects.requireNonNull(workflow).getCurrentVersion());
	}

	public WorkflowDtoV1Exporter(AGWorkflowVersion workflowVersion) {

		this.workflowVersion = Objects.requireNonNull(workflowVersion);
		this.skipNodePositions = false;
	}

	public WorkflowDtoV1Exporter setSkipNodePositions(boolean skipNodePositions) {

		this.skipNodePositions = skipNodePositions;
		return this;
	}

	public WorkflowDtoV1 exportWorkflow() {

		this.workflowDto = createWorkflowDto();
		this.nodeIndexMap = new IndexHashMap<>();
		this.nodeDtoMap = new HashMap<>();
		this.actionDtoMap = new HashMap<>();
		this.transitionDtoMap = new HashMap<>();

		loadNodes().forEach(this::addNodeDto);
		loadNodeActions().forEach(this::addNodeAction);
		loadNodeActionPermissions().forEach(this::addNodeActionPermission);
		loadNodePreconditions().forEach(this::addNodePrecondition);
		loadTransitions().forEach(this::addTransition);
		loadTransitionPermissions().forEach(this::addTransitionPermission);

		workflowDto.rootNode = nodeIndexMap.getIndex(workflowVersion.getRootNode());
		return workflowDto;
	}

	private WorkflowDtoV1 createWorkflowDto() {

		var dto = new WorkflowDtoV1();
		dto.name = workflowVersion.getWorkflow().getName();
		dto.entityType = workflowVersion.getWorkflow().getEntityTable().getUuidString();
		dto.nodes = new ArrayList<>();
		dto.transitions = new ArrayList<>();
		dto.rootNode = -1;
		return dto;
	}

	// ------------------------------ nodes ------------------------------ //

	private List<AGWorkflowNode> loadNodes() {

		return AGWorkflowNode.TABLE//
			.createSelect()
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(workflowVersion))
			.orderBy(AGWorkflowNode.ID)
			.list();
	}

	private void addNodeDto(AGWorkflowNode node) {

		var nodeDto = new WorkflowDtoV1.Node();
		nodeDto.name = node.getName();
		nodeDto.x = skipNodePositions? 0 : node.getXCoordinate();
		nodeDto.y = skipNodePositions? 0 : node.getYCoordinate();
		nodeDto.actions = new ArrayList<>();
		nodeDto.preconditions = new ArrayList<>();

		workflowDto.nodes.add(nodeDto);
		nodeIndexMap.add(node);
		nodeDtoMap.put(node, nodeDto);
	}

	// ------------------------------ node actions ------------------------------ //

	private List<AGWorkflowNodeAction> loadNodeActions() {

		return AGWorkflowNodeAction.TABLE//
			.createSelect()
			.where(AGWorkflowNodeAction.ACTIVE)
			.orderBy(AGWorkflowNodeAction.ID)
			.join(AGWorkflowNodeAction.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(workflowVersion))
			.list();
	}

	private void addNodeAction(AGWorkflowNodeAction action) {

		var actionDto = new WorkflowDtoV1.NodeAction();
		actionDto.action = action.getAction().getUuidString();
		actionDto.permissions = new ArrayList<>();

		nodeDtoMap.get(action.getWorkflowNode()).actions.add(actionDto);
		actionDtoMap.put(action, actionDto);
	}

	// ------------------------------ node action permissions ------------------------------ //

	private List<AGWorkflowNodeActionPermission> loadNodeActionPermissions() {

		return AGWorkflowNodeActionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowNodeActionPermission.ACTIVE)
			.orderBy(AGWorkflowNodeActionPermission.ID)
			.join(AGWorkflowNodeActionPermission.WORKFLOW_NODE_ACTION)
			.where(AGWorkflowNodeAction.ACTIVE)
			.join(AGWorkflowNodeAction.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(workflowVersion))
			.list();
	}

	private void addNodeActionPermission(AGWorkflowNodeActionPermission permission) {

		actionDtoMap.get(permission.getWorkflowNodeAction()).permissions.add(permission.getPermissionUuid().getUuidString());
	}

	// ------------------------------ node preconditions ------------------------------ //

	private List<AGWorkflowNodePrecondition> loadNodePreconditions() {

		return AGWorkflowNodePrecondition.TABLE//
			.createSelect()
			.where(AGWorkflowNodePrecondition.ACTIVE)
			.orderBy(AGWorkflowNodePrecondition.ID)
			.join(AGWorkflowNodePrecondition.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(workflowVersion))
			.list();
	}

	private void addNodePrecondition(AGWorkflowNodePrecondition precondition) {

		nodeDtoMap.get(precondition.getWorkflowNode()).preconditions.add(precondition.getFunction().getUuidString());
	}

	// ------------------------------ transitions ------------------------------ //

	private List<AGWorkflowTransition> loadTransitions() {

		return AGWorkflowTransition.TABLE//
			.createSelect()
			.where(AGWorkflowTransition.ACTIVE)
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(workflowVersion))
			.orderBy(AGWorkflowTransition.ID)
			.list();
	}

	private void addTransition(AGWorkflowTransition transition) {

		var transitionDto = new WorkflowDtoV1.Transition();
		transitionDto.name = transition.getName();
		transitionDto.sourceNode = nodeIndexMap.getIndex(transition.getSourceNode());
		transitionDto.targetNode = nodeIndexMap.getIndex(transition.getTargetNode());
		transitionDto.notify = transition.isNotify();
		transitionDto.autoTransition = transition.isAutoTransition();
		transitionDto.requiredVotes = transition.getRequiredVotes();
		transitionDto.htmlColor = transition.getHtmlColor();
		transitionDto.icon = Optional.ofNullable(transition.getTransitionIcon()).map(it -> it.getName()).orElse("");
		transitionDto.sideEffect = Optional.ofNullable(transition.getSideEffect()).map(it -> it.getUuidString()).orElse("");
		transitionDto.permissions = new ArrayList<>();

		workflowDto.transitions.add(transitionDto);
		transitionDtoMap.put(transition, transitionDto);
	}

	// ------------------------------ transition permissions ------------------------------ //

	private List<AGWorkflowTransitionPermission> loadTransitionPermissions() {

		return AGWorkflowTransitionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowTransitionPermission.ACTIVE)
			.orderBy(AGWorkflowTransitionPermission.ID)
			.join(AGWorkflowTransitionPermission.TRANSITION)
			.where(AGWorkflowTransition.ACTIVE)
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(workflowVersion))
			.list();
	}

	private void addTransitionPermission(AGWorkflowTransitionPermission permission) {

		transitionDtoMap.get(permission.getTransition()).permissions.add(permission.getPermission().getUuidString());
	}
}
