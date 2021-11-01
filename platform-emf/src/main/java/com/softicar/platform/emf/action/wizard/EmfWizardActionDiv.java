package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

class EmfWizardActionDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final R tableRow;
	private final IEmfFormBody<R> formBody;
	private final List<EmfWizardActionSectionDiv<R>> sectionDivs;

	public EmfWizardActionDiv(AbstractEmfWizardAction<R> action, R tableRow, IEmfFormBody<R> formBody) {

		this.tableRow = tableRow;
		this.formBody = formBody;
		this.sectionDivs = new ArrayList<>();

		for (IEmfWizardActionStep<R> step: action.getSteps()) {
			EmfWizardActionSectionDiv<R> sectionDiv = new EmfWizardActionSectionDiv<>(formBody, this, step);
			sectionDivs.add(sectionDiv);
			appendChild(sectionDiv);
		}

		openFirstSectionTodo();
	}

	public void closeAllOpenSectionDivs() {

		for (EmfWizardActionSectionDiv<R> sectionDiv: sectionDivs) {
			if (sectionDiv.isOpen()) {
				sectionDiv.setOpen(false);
			}
		}
	}

	public R getTableRow() {

		return tableRow;
	}

	public IEmfFormBody<R> getFormBody() {

		return formBody;
	}

	private void openFirstSectionTodo() {

		for (EmfWizardActionSectionDiv<R> sectionDiv: sectionDivs) {
			if (!sectionDiv.isDone() && sectionDiv.isDoable()) {
				sectionDiv.setOpen(true);
				break;
			}
		}
	}
}
