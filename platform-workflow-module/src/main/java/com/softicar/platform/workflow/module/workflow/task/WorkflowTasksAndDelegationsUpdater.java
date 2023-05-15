package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Collection;

/**
 * Updates workflow tasks and delegations for given workflow items.
 *
 * @author Alexander Schmidt
 */
public class WorkflowTasksAndDelegationsUpdater {

	private ExceptionsCollector exceptionsCollector;

	public WorkflowTasksAndDelegationsUpdater() {

		this.exceptionsCollector = null;
	}

	/**
	 * Specifies an {@link ExceptionsCollector} to collect exceptions that occur
	 * during task creation.
	 *
	 * @param exceptionsCollector
	 *            the {@link ExceptionsCollector} (may be <i>null</i>)
	 * @return this
	 */
	public WorkflowTasksAndDelegationsUpdater setExceptionsCollector(ExceptionsCollector exceptionsCollector) {

		this.exceptionsCollector = exceptionsCollector;
		return this;
	}

	/**
	 * Closes all existing tasks and delegations for the given workflow items.
	 * Then, inserts new tasks.
	 * <p>
	 * If an {@link ExceptionsCollector} was specified, exceptions that occur
	 * during task creation are collected instead of being thrown.
	 * <p>
	 * These operations are intentionally handled in <b>separate database
	 * transactions</b>:
	 * <ul>
	 * <li>All tasks and delegations are closed in an initial, single
	 * transaction.</li>
	 * <li>New tasks are inserted in separate, subsequent transactions (i.e. one
	 * per workflow item).</li>
	 * </ul>
	 * If the effects of this method on the database shall be atomic, the call
	 * must be wrapped in another {@link DbTransaction}. In that case, no
	 * {@link ExceptionsCollector} shall be used.
	 *
	 * @param items
	 */
	public void updateAll(Collection<AGWorkflowItem> items) {

		// close existing tasks and delegations in a single transaction
		try (var transaction = new DbTransaction()) {
			items.forEach(item -> {
				new WorkflowTaskManager(item).closeTasksAndDelegations();
			});
			transaction.commit();
		}

		// create new tasks in distinct transactions, and catch exceptions if appropriate
		items.forEach(item -> {
			try (var transaction = new DbTransaction()) {
				new WorkflowTaskManager(item).insertTasks();
				transaction.commit();
			} catch (Throwable throwable) {
				if (exceptionsCollector != null) {
					Log.fverbose("FAILURE -- see exceptions below");
					exceptionsCollector.add(new WorkflowTaskCreationFailedException(throwable, item));
				} else {
					throw throwable;
				}
			}
		});
	}
}
