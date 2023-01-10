package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.transients.AbstractTransientSetField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkflowUserField<R extends IDbTableRow<R, ?>> extends AbstractTransientSetField<R, String> {

	private final IDbForeignField<R, AGWorkflowItem> workflowItemField;

	public WorkflowUserField(IDbForeignField<R, AGWorkflowItem> workflowItemField) {

		super(HashSet::new);
		this.workflowItemField = workflowItemField;
	}

	@Override
	protected void loadValues(Set<R> rows, IValueAccumulator<R, String> accumulator) {

		var items = workflowItemField.prefetch(rows);
		AGWorkflowTask.WORKFLOW_ITEM.prefetchData(items);
		AGWorkflowItem.OPEN_TASKS.prefetchAll(items);

		rows//
			.stream()
			.forEach(row -> accumulator.addAll(row, getUserTaskAgeStrings(row)));
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_USERS;
	}

	private List<String> getUserTaskAgeStrings(R row) {

		Day today = Day.today();
		AGWorkflowItem workflowItem = workflowItemField.getValue(row);
		Set<AGWorkflowTask> tasks = Optional.ofNullable(workflowItem).map(item -> AGWorkflowItem.OPEN_TASKS.getValue(item)).orElse(Collections.emptySet());
		return getUserTaskCreationMap(tasks)//
			.entrySet()
			.stream()
			.map(entry -> getUserTaskAgeString(entry.getKey(), today.minus(entry.getValue().getDay())))
			.collect(Collectors.toList());
	}

	private Map<Delegation, DayTime> getUserTaskCreationMap(Set<AGWorkflowTask> tasks) {

		AGWorkflowTask.CREATION_TRANSACTION.prefetchAll(tasks);

		Map<Delegation, DayTime> userTaskCreationMap = new HashMap<>();
		for (AGWorkflowTask task: tasks) {
			Optional<AGTransaction> creationTransaction = Optional.ofNullable(AGWorkflowTask.CREATION_TRANSACTION.getValue(task));

			if (task.hasActiveDelegation()) {
				AGWorkflowTaskDelegation delegation = task.getActiveDelegation().get();
				Optional<DayTime> lastModificationTimeFormLog = delegation.getLastModificationTimeFormLog();
				if (lastModificationTimeFormLog.isPresent()) {
					userTaskCreationMap.merge(new Delegation(task.getUser(), delegation.getTargetUser()), lastModificationTimeFormLog.get(), DayTime::min);
				}
			} else if (creationTransaction.isPresent()) {
				userTaskCreationMap.merge(new Delegation(task.getUser(), task.getUser()), creationTransaction.get().getAt(), DayTime::min);
			}
		}
		return userTaskCreationMap;
	}

	private String getUserTaskAgeString(Delegation userAndCurrentDelegation, int days) {

		if (userAndCurrentDelegation.isDelegated()) {
			return String
				.format("%s (%s %s)", userAndCurrentDelegation.getOriginalUser().toDisplayWithoutId(), days, days == 1? WorkflowI18n.DAY : WorkflowI18n.DAYS);
		} else {
			return String
				.format(
					"%s -> %s (%s %s)",
					userAndCurrentDelegation.getOriginalUser().toDisplayWithoutId(),
					userAndCurrentDelegation.getDelegatee().toDisplayWithoutId(),
					days,
					days == 1? WorkflowI18n.DAY : WorkflowI18n.DAYS);
		}

	}

	private static class Delegation extends Pair<AGUser, AGUser> {

		Delegation(AGUser originalUser, AGUser delegatee) {

			super(originalUser, delegatee);
		}

		AGUser getOriginalUser() {

			return getFirst();
		}

		AGUser getDelegatee() {

			return getSecond();
		}

		boolean isDelegated() {

			return getOriginalUser() == getDelegatee();
		}
	}
}
