package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfWizardActionSectionDiv<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

	private final EmfWizardActionHolder<R> actionHolder;

	public EmfWizardActionSectionDiv(IEmfFormBody<R> formBody, EmfWizardActionDiv<R> actionDiv, IEmfWizardActionStep<R> action) {

		super(//
			formBody,
			new EmfWizardActionSectionHeader<>(actionDiv, action),
			new EmfWizardActionCallbackAfterOpened<>(actionDiv, action),
			new EmfWizardActionTitleModifier<>(actionDiv, action));
		this.actionHolder = new EmfWizardActionHolder<>(actionDiv, action);
	}

	public boolean isDone() {

		return actionHolder.isDone();
	}

	public boolean isDoable() {

		return actionHolder.isDoable();
	}
}
