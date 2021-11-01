package com.softicar.platform.workflow.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.IWorkflowAction;
import com.softicar.platform.workflow.module.workflow.node.action.role.AGWorkflowNodeActionRole;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.role.AGWorkflowTransitionRole;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public interface WorkflowModuleTestFixtureMethods extends CoreModuleTestFixtureMethods {

	default AGWorkflowModuleInstance insertWorkflowModuleInstance() {

		return insertStandardModuleInstance(AGWorkflowModuleInstance.TABLE);
	}

	default AGWorkflow insertWorkflow(AGWorkflowModuleInstance moduleInstance, String name,
			Class<? extends IWorkflowTableReferencePoint<?>> referencePointClass) {

		return new AGWorkflow()//
			.setModuleInstance(moduleInstance)
			.setName(name)
			.setEntityTable(AGUuid.getOrCreate(referencePointClass))
			.setActive(true)
			.save();
	}

	default AGWorkflowVersion insertWorkflowVersion(AGWorkflow workflow, Boolean draft) {

		return new AGWorkflowVersion()//
			.setWorkflow(workflow)
			.setDraft(draft)
			.save();
	}

	default AGWorkflowNode insertWorkflowNode(AGWorkflowVersion workflowVersion, String name) {

		return insertWorkflowNode(workflowVersion, name, 0, 0);
	}

	default AGWorkflowNode insertWorkflowNode(AGWorkflowVersion workflowVersion, String name, int xCoordinate, int yCoordinate) {

		return new AGWorkflowNode()//
			.setWorkflowVersion(workflowVersion)
			.setName(name)
			.setXCoordinate(xCoordinate)
			.setYCoordinate(yCoordinate)
			.save();
	}

	default AGWorkflowItem insertWorkflowItem(AGWorkflowNode workflowNode) {

		return new AGWorkflowItem()//
			.setWorkflow(workflowNode.getWorkflowVersion().getWorkflow())
			.setWorkflowNode(workflowNode)
			.save();
	}

	default AGWorkflowTransition insertWorkflowAutoTransition(AGWorkflowNode sourceNode, AGWorkflowNode targetNode, String name) {

		return new AGWorkflowTransition()//
			.setWorkflowVersion(sourceNode.getWorkflowVersion())
			.setName(name)
			.setSourceNode(sourceNode)
			.setTargetNode(targetNode)
			.setAutoTransition(true)
			.setRequiredVotes("0")
			.setNotify(false)
			.save();
	}

	default AGWorkflowTransition insertWorkflowAutoTransition(String name, AGWorkflowNode sourceNode, AGWorkflowNode targetNode) {

		return new AGWorkflowTransition()//
			.setWorkflowVersion(sourceNode.getWorkflowVersion())
			.setName(name)
			.setSourceNode(sourceNode)
			.setTargetNode(targetNode)
			.setAutoTransition(true)
			.setRequiredVotes("0")
			.setNotify(false)
			.save();
	}

	default AGWorkflowTransition insertWorkflowTransition(String name, AGWorkflowNode sourceNode, AGWorkflowNode targetNode, String requiredVotes,
			boolean notify, IEmfStaticRole<?> role) {

		AGWorkflowTransition transition = new AGWorkflowTransition()//
			.setWorkflowVersion(sourceNode.getWorkflowVersion())
			.setName(name)
			.setSourceNode(sourceNode)
			.setTargetNode(targetNode)
			.setRequiredVotes(requiredVotes)
			.setNotify(notify)
			.save();

		new AGWorkflowTransitionRole()//
			.setTransition(transition)
			.setRole(AGUuid.getOrCreate(role.getAnnotatedUuid()))
			.save();

		return transition;
	}

	default AGWorkflowTransitionExecution insertWorkflowTransitionExecution(AGWorkflowTask task, AGWorkflowTransition transition) {

		return new AGWorkflowTransitionExecution()//
			.setWorkflowTask(task)
			.setWorkflowTransition(transition)
			.save();
	}

	default AGWorkflowNodePrecondition insertWorkflowNodePrecondition(AGWorkflowNode node, Class<? extends IWorkflowPrecondition<?>> condition) {

		return new AGWorkflowNodePrecondition()//
			.setWorkflowNode(node)
			.setFunction(AGUuid.getOrCreate(condition))
			.save();
	}

	default AGWorkflowNodeAction insertWorkflowNodeAction(AGWorkflowNode node, Class<? extends IWorkflowAction<?>> actionClass, IEmfStaticRole<?> role) {

		AGWorkflowNodeAction action = new AGWorkflowNodeAction()//
			.setWorkflowNode(node)
			.setAction(AGUuid.getOrCreate(actionClass))
			.save();

		new AGWorkflowNodeActionRole()//
			.setWorkflowNodeAction(action)
			.setRoleUuid(AGUuid.getOrCreate(role.getAnnotatedUuid()))
			.save();

		return action;
	}

	default AGWorkflowItemMessage insertWorkflowItemMessage(AGWorkflowItem item, String freeText) {

		return new AGWorkflowItemMessage()//
			.setText(freeText)
			.setWorkflowItem(item)
			.save();
	}

	default AGWorkflowTask insertWorkflowTaskClosed(AGUser user, AGWorkflowItem item) {

		return insertWorkflowTask(true, user, item);
	}

	default AGWorkflowTask insertWorkflowTaskOpen(AGUser user, AGWorkflowItem item) {

		return insertWorkflowTask(false, user, item);
	}

	default AGWorkflowTask insertWorkflowTask(boolean closed, AGUser user, AGWorkflowItem item) {

		return new AGWorkflowTask()//
			.setClosed(closed)
			.setNotify(true)
			.setUser(user)
			.setWorkflowItem(item)
			.save();
	}

	default AGWorkflowTaskDelegation insertWorkflowTaskDelegation(AGWorkflowTask task, Boolean active, AGUser targetUser, AGUser delegatedByUser) {

		return AGWorkflowTaskDelegation.TABLE//
			.getOrCreate(task)
			.setActive(active)
			.setTargetUser(targetUser)
			.setDelegatedBy(delegatedByUser)
			.save();
	}
}
