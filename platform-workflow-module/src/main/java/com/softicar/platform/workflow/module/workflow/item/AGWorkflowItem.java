package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTasksLoader;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.WorkflowAutoTransitionsExecutor;
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

	public Collection<AGWorkflowTask> getOpenTasksFor(AGUser user) {

		return new WorkflowTasksLoader(this).getOpenTasksFor(user);
	}

	public Collection<AGWorkflowTask> getAllWorkflowTasks() {

		return AGWorkflowTask.createSelect().where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(this)).list();
	}

	public Collection<AGWorkflowAutoTransitionExecution> loadAutoTransitionExecutions() {

		return AGWorkflowAutoTransitionExecution.TABLE//
			.createSelect()
			.where(AGWorkflowAutoTransitionExecution.WORKFLOW_ITEM.isEqual(this))
			.list();
	}

	public Collection<Set<AGWorkflowTransitionExecution>> loadTransitionExecutionSets() {

		return loadTransitionExecutionMap().getSets();
	}

	/**
	 * Executes the longest possible cascade of auto transitions for this
	 * {@link AGWorkflowItem}.
	 */
	public void executeAllAutoTransitions() {

		new WorkflowAutoTransitionsExecutor().setWorkflowItemFilter(this).executeTransitions();
	}

	private SetMap<AGWorkflowTransition, AGWorkflowTransitionExecution> loadTransitionExecutionMap() {

		SetMap<AGWorkflowTransition, AGWorkflowTransitionExecution> transitionExecutionMap = new SetMap<>();

		for (AGWorkflowTransitionExecution transitionExecution: AGWorkflowTransitionExecution//
			.createSelect()
			.join(AGWorkflowTransitionExecution.WORKFLOW_TASK)
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(getThis()))) {

			transitionExecutionMap.addToSet(transitionExecution.getWorkflowTransition(), transitionExecution);
		}
		return transitionExecutionMap;
	}

}
