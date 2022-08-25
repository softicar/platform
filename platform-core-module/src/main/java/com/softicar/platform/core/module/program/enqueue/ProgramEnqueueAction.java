package com.softicar.platform.core.module.program.enqueue;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.ProgramPredicates;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfSecondaryAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ProgramEnqueueAction implements IEmfSecondaryAction<AGProgram> {

	@Override
	public IEmfPredicate<AGProgram> getPrecondition() {

		return ProgramPredicates.NOT_QUEUED;
	}

	@Override
	public IEmfPermission<AGProgram> getRequiredPermission() {

		return CoreModule.getOperationPermission();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.EXECUTE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.ENQUEUE;
	}

	@Override
	public void handleClick(AGProgram program) {

		try (var transaction = new DbTransaction()) {
			program.lockProgramState();
			if (!program.isQueued()) {
				program//
					.getState()
					.setQueuedAt(DayTime.now().truncateSeconds())
					.setQueuedBy(CurrentUser.get())
					.save();
			}
			transaction.commit();
			CurrentDomDocument.get().getRefreshBus().setChanged(program);
		}
	}
}
