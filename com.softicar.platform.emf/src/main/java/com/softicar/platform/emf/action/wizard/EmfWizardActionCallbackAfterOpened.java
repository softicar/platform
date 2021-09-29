package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Consumer;

class EmfWizardActionCallbackAfterOpened<R extends IEmfTableRow<R, ?>> extends EmfWizardActionHolder<R> implements Consumer<IEmfFormSectionDiv<R>> {

	public EmfWizardActionCallbackAfterOpened(EmfWizardActionDiv<R> actionDiv, IEmfWizardActionStep<R> action) {

		super(actionDiv, action);
	}

	@Override
	public void accept(IEmfFormSectionDiv<R> actionContainer) {

		// TODO: re-enable auto closing of other sections when opening a specific one?
//		getActionDiv().closeAllOpenSectionDivs();
		getAction().integrate(getActionDiv().getTableRow(), actionContainer);
	}
}
