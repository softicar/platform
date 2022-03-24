package com.softicar.platform.emf.form;

import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBus;
import com.softicar.platform.emf.form.section.IEmfFormSectionContainer;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfFormBody<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormBody<R> {

	private final EmfForm<R> form;
	private final R tableRow;
	private final EmfFormBodyUpperPart<R> upperPart;
	private final EmfFormBodyLowerPart<R> lowerPart;
	private boolean creationMode;

	public EmfFormBody(EmfForm<R> form) {

		this.form = form;
		this.tableRow = form.getTableRow();
		this.upperPart = new EmfFormBodyUpperPart<>(this);
		this.lowerPart = new EmfFormBodyLowerPart<>(this);
		appendChild(upperPart);
		appendChild(lowerPart);

		if (tableRow.impermanent() || form.isDirectEditingEnabled()) {
			enterEditMode();
		} else {
			enterViewMode();
		}
	}

	@Override
	public EmfForm<R> getForm() {

		return form;
	}

	@Override
	public R getTableRow() {

		return tableRow;
	}

	@Override
	public IDomRefreshBus getRefreshBus() {

		return getDomDocument().getRefreshBus();
	}

	@Override
	public void queueEntityForRefresh() {

		tableRow//
			.getAttributeOwners()
			.stream()
			.forEach(getDomDocument().getRefreshBus()::setChanged);
		form.focusFrame();
	}

	@Override
	public void showStandardSectionContainer() {

		lowerPart.showStandardSectionContainer();
	}

	@Override
	public void showSectionContainer(IEmfFormSectionContainer sectionContainer) {

		lowerPart.showSectionContainer(sectionContainer);
	}

	@Override
	public void showInteractiveRefreshSectionContainer() {

		lowerPart.showInteractiveRefreshSectionContainer();
	}

	@Override
	public void enterEditMode() {

		if (!upperPart.isEditMode()) {
			this.creationMode = tableRow.impermanent();
			if (ensureEntityIsFresh()) {
				upperPart.enterEditMode();
				lowerPart.showSectionContainer(new EmfFormSaveOrCancelActions<>(this, upperPart.getAttributesDiv()));
				if (creationMode) {
					form.handleModeChange(EmfFormMode.CREATION);
				} else {
					form.handleModeChange(EmfFormMode.EDIT);
				}
			} else {
				form.handleModeChange(EmfFormMode.VIEW);
				upperPart.enterViewMode();
			}
		}
	}

	@Override
	public void cancelEditMode() {

		if (upperPart.isEditMode()) {
			if (form.isDirectEditingEnabled() || creationMode) {
				form.closeFrame();
			} else {
				enterViewMode();
			}
		}
	}

	@Override
	public void finishEditMode(boolean closeAfterFinish) {

		if (upperPart.isEditMode()) {
			if (closeAfterFinish) {
				form.closeFrame();
			} else {
				enterViewMode();
			}
			queueEntityForRefresh();
		}
	}

	@Override
	public void refreshAfterConcurrentModification() {

		DbTableRowCaches.invalidateAll();
		queueEntityForRefresh();

		if (upperPart.isEditMode()) {
			form.handleModeChange(EmfFormMode.VIEW);
			upperPart.enterViewMode();
		}
	}

	@Override
	public void closeFrame() {

		form.closeFrame();
	}

	@Override
	public void applyCallbackAfterCreation() {

		form.getCallbackAfterCreation().accept(tableRow);
	}

	protected void refresh() {

		if (!upperPart.isEditMode() && ensureEntityIsFresh()) {
			upperPart.refresh();
			lowerPart.showStandardSectionContainer();
		}
	}

	private void enterViewMode() {

		if (tableRow.isFresh()) {
			upperPart.enterViewMode();
			lowerPart.showStandardSectionContainer();
		} else {
			upperPart.enterViewModeIfNoInputChanged();
			lowerPart.showInteractiveRefreshSectionContainer();
		}
		form.handleModeChange(EmfFormMode.VIEW);
	}

	private boolean ensureEntityIsFresh() {

		if (tableRow.isFresh()) {
			return true;
		} else {
			lowerPart.showInteractiveRefreshSectionContainer();
			return false;
		}
	}
}
