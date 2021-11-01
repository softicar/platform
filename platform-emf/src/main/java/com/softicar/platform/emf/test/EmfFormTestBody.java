package com.softicar.platform.emf.test;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBus;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.IEmfFormSectionContainer;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormTestBody<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormBody<R> {

	private final R tableRow;
	private final EmfFormTestContainer<R> actionContainer;

	public EmfFormTestBody(R tableRow) {

		this.tableRow = tableRow;
		this.actionContainer = new EmfFormTestContainer<>(this);

		appendChild(actionContainer);
	}

	public EmfFormTestBody<R> showAction(IEmfPrimaryAction<R> action) {

		action.integrate(tableRow, actionContainer);
		return this;
	}

	@Override
	public IEmfForm<R> getForm() {

		throw new UnsupportedOperationException();
	}

	@Override
	public R getTableRow() {

		return tableRow;
	}

	@Override
	public IDomRefreshBus getRefreshBus() {

		return actionContainer.getDomDocument().getRefreshBus();
	}

	@Override
	public void queueEntityForRefresh() {

		removeChildren();
		appendChild(actionContainer);
	}

	@Override
	public void showStandardSectionContainer() {

		removeChildren();
		appendChild(actionContainer);
	}

	@Override
	public void showSectionContainer(IEmfFormSectionContainer sectionContainer) {

		removeChildren();
		appendChild(sectionContainer);
	}

	@Override
	public void showInteractiveRefreshSectionContainer() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void enterEditMode() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void finishEditMode(boolean closeAfterFinish) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void cancelEditMode() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void closeFrame() {

		// nothing to do
	}

	@Override
	public void applyCallbackAfterCreation() {

		// nothing to do
	}
}
