package com.softicar.platform.emf.management;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

class EmfManagementActionColumnHandler<R extends IEmfTableRow<R, P>, P> implements IEmfDataTableActionColumnHandler<R> {

	private final IEmfTable<R, P, ?> entityTable;

	public EmfManagementActionColumnHandler(IEmfTable<R, P, ?> entityTable) {

		this.entityTable = entityTable;
	}

	@Override
	public void buildCell(IEmfDataTableActionCell<R> cell, R tableRow) {

		cell.getContentContainer().appendChild(new ActionsPopoverButton(tableRow));
	}

	@Override
	public void prefetchData(Collection<R> rows) {

		entityTable//
			.getEmfTableConfiguration()
			.getEditPredicate()
			.prefetchData(rows);
	}

	private class ActionsPopoverButton extends DomButton {

		public ActionsPopoverButton(R tableRow) {

			setMarker(EmfManagementMarker.ACTIONS_POPOVER_BUTTON);
			addCssClass(EmfCssClasses.EMF_MANAGEMENT_ACTIONS_BUTTON);
			setIcon(EmfImages.MANAGEMENT_ACTIONS.getResource());
			setTitle(EmfI18n.ACTIONS);
			setClickCallback(() -> new EmfManagementActionPopover<>(tableRow).open());
		}
	}
}
