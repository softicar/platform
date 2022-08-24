package com.softicar.platform.core.module.program.abort;

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

public class ProgramAbortAction implements IEmfSecondaryAction<AGProgram> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.ABORT;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MEDIA_STOP.getResource();
	}

	@Override
	public IEmfPredicate<AGProgram> getPrecondition() {

		return new EmfPredicate<>(//
			CoreI18n.QUEUED_OR_RUNNING,
			AGProgram::isQueuedOrRunning);
	}

	@Override
	public IEmfPermission<AGProgram> getRequiredPermission() {

		return CoreModule.getAdministationPermission();
	}

	@Override
	public void handleClick(AGProgram program) {

		new ProgramAbortRequester(program).requestAbort();
		CurrentDomDocument.get().getRefreshBus().setChanged(program);
	}
}
