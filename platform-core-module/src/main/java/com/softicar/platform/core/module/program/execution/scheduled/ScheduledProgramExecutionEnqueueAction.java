package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ScheduledProgramExecutionEnqueueAction implements IEmfManagementAction<AGScheduledProgramExecution> {

	@Override
	public IEmfPredicate<AGScheduledProgramExecution> getPrecondition() {

		return ScheduledProgramExecutionPredicates.IS_NOT_QUEUED//
			.and(ScheduledProgramExecutionPredicates.IS_NOT_RUNNING);
	}

	@Override
	public IEmfRole<AGScheduledProgramExecution> getAuthorizedRole() {

		return CoreRoles.SUPER_USER.toOtherEntityRole();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.EXECUTE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Enqueue");
	}

	@Override
	public void handleClick(AGScheduledProgramExecution tableRow) {

		AGProgram//
			.loadOrInsert(tableRow.getProgramUuid())
			.setQueuedAt(DayTime.now().truncateSeconds())
			.save();
	}
}
