package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.Collection;
import java.util.Set;

public class AGWorkflowItem extends AGWorkflowItemGenerated implements IEmfObject<AGWorkflowItem> {

	public static final OpenTasksField OPEN_TASKS = new OpenTasksField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getEntityOrThrow().toDisplayWithoutId();
	}

	public IEmfEntity<?, ?> getEntityOrThrow() {

		return getWorkflow().getTableReferencePointOrThrow().getEntityOrThrow(this);
	}

	public Collection<AGWorkflowTask> getAllWorkflowTasks() {

		return AGWorkflowTask.createSelect().where(AGWorkflowTask.WORKFLOW_ITEM.equal(this)).list();
	}

	public Collection<AGWorkflowAutoTransitionExecution> loadAutoTransitionExecutions() {

		return AGWorkflowAutoTransitionExecution.TABLE//
			.createSelect()
			.where(AGWorkflowAutoTransitionExecution.WORKFLOW_ITEM.equal(this))
			.list();
	}

	public Collection<Set<AGWorkflowTransitionExecution>> loadTransitionExecutionSets() {

		return loadTransitionExecutionMap().getSets();
	}

	private SetMap<AGWorkflowTransition, AGWorkflowTransitionExecution> loadTransitionExecutionMap() {

		SetMap<AGWorkflowTransition, AGWorkflowTransitionExecution> transitionExecutionMap = new SetMap<>();

		for (AGWorkflowTransitionExecution transitionExecution: AGWorkflowTransitionExecution//
			.createSelect()
			.join(AGWorkflowTransitionExecution.WORKFLOW_TASK)
			.where(AGWorkflowTask.WORKFLOW_ITEM.equal(getThis()))) {

			transitionExecutionMap.addToSet(transitionExecution.getWorkflowTransition(), transitionExecution);
		}
		return transitionExecutionMap;
	}

}
