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

		workflowVersion.getAllActiveWorkflowNodes().forEach(this::addNodeDto);
		workflowVersion.getAllActiveWorkflowNodeActions().forEach(this::addNodeAction);
		workflowVersion.getAllActiveWorkflowNodeActionPermissions().forEach(this::addNodeActionPermission);
		workflowVersion.getAllActiveWorkflowNodePreconditions().forEach(this::addNodePrecondition);
		workflowVersion.getAllActiveTransitions().forEach(this::addTransition);
		workflowVersion.getAllActiveTransitionPermissions().forEach(this::addTransitionPermission);

		workflowDto.rootNode = Optional.ofNullable(workflowVersion.getRootNode()).map(nodeIndexMap::getIndex).orElse(null);
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

	private void addNodeAction(AGWorkflowNodeAction action) {

		var actionDto = new WorkflowDtoV1.NodeAction();
		actionDto.action = action.getAction().getUuidString();
		actionDto.permissions = new ArrayList<>();

		nodeDtoMap.get(action.getWorkflowNode()).actions.add(actionDto);
		actionDtoMap.put(action, actionDto);
	}

	// ------------------------------ node action permissions ------------------------------ //

	private void addNodeActionPermission(AGWorkflowNodeActionPermission permission) {

		actionDtoMap.get(permission.getWorkflowNodeAction()).permissions.add(permission.getPermissionUuid().getUuidString());
	}

	// ------------------------------ node preconditions ------------------------------ //

	private void addNodePrecondition(AGWorkflowNodePrecondition precondition) {

		nodeDtoMap.get(precondition.getWorkflowNode()).preconditions.add(precondition.getFunction().getUuidString());
	}

	// ------------------------------ transitions ------------------------------ //

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

	private void addTransitionPermission(AGWorkflowTransitionPermission permission) {

		transitionDtoMap.get(permission.getTransition()).permissions.add(permission.getPermission().getUuidString());
	}
}
