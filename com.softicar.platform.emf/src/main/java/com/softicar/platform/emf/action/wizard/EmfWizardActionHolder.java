package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfWizardActionHolder<R extends IEmfTableRow<R, ?>> {

	private final EmfWizardActionDiv<R> actionDiv;
	private final IEmfWizardActionStep<R> action;

	public EmfWizardActionHolder(EmfWizardActionDiv<R> actionDiv, IEmfWizardActionStep<R> action) {

		this.actionDiv = actionDiv;
		this.action = action;
	}

	protected EmfWizardActionDiv<R> getActionDiv() {

		return actionDiv;
	}

	protected IEmfWizardActionStep<R> getAction() {

		return action;
	}

	public boolean isDone() {

		return action.isDone(actionDiv.getTableRow());
	}

	public boolean isDoable() {

		return action.isDoable(actionDiv.getTableRow());
	}

	public void undo() {

		action.undo(actionDiv.getTableRow());
		actionDiv.getFormBody().queueEntityForRefresh();
	}
}
