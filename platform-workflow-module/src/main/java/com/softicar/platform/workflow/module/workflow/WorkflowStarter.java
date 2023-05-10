package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import java.util.Objects;

public class WorkflowStarter {

	private final AGWorkflow workflow;

	public WorkflowStarter(AGWorkflow workflow) {

		this.workflow = Objects.requireNonNull(workflow);
	}

	/**
	 * Starts the workflow for the given {@link IWorkflowableObject}.
	 * <p>
	 * Executes auto transitions and generates tasks if appropriate.
	 *
	 * @param object
	 *            the {@link IWorkflowableObject} (never <i>null</i>)
	 */
	public void startWorkflow(IWorkflowableObject<?> object) {

		Objects.requireNonNull(object);

		try (var transaction = new DbTransaction()) {
			if (object.getWorkflowItem() == null) {
				AGWorkflowItem item = new AGWorkflowItem() //
					.setWorkflow(workflow)
					.setWorkflowNode(workflow.getCurrentVersion().getRootNode())
					.save();

				object.setWorkflowItem(item);
				object.save();
			} else {
				object//
					.getWorkflowItem()
					.setWorkflow(workflow)
					.setWorkflowNode(workflow.getCurrentVersion().getRootNode())
					.save();
			}

			var item = object.getWorkflowItem();
			if (!item.executeAllAutoTransitions()) {
				new WorkflowTaskManager(item).insertTasks();
			}

			transaction.commit();
		}
	}
}
