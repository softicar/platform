package com.softicar.platform.core.module.program.unqueue;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfSecondaryAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class ProgramUnqueueAction implements IEmfSecondaryAction<AGProgram> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.REMOVE_FROM_QUEUE;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.QUEUE_REMOVE.getResource();
	}

	@Override
	public IEmfPredicate<AGProgram> getPrecondition() {

		return new EmfPredicate<>(//
			CoreI18n.QUEUED,
			AGProgram::isQueued);
	}

	@Override
	public IEmfPermission<AGProgram> getRequiredPermission() {

		return CoreModule.getAdministationPermission();
	}

	@Override
	public void handleClick(AGProgram program) {

		new ProgramUnqueuer(program).removeFromQueue();
		CurrentDomDocument.get().getRefreshBus().setChanged(program);
	}
}
