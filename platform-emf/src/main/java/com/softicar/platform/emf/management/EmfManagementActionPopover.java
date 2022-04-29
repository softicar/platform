package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.style.CssColorEnum;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.marker.EmfManagementActionMarker;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfManagementActionPopover<R extends IEmfTableRow<R, P>, P> extends DomPopover {

	private final IEmfTable<R, P, ?> entityTable;
	private final R tableRow;
	private final ExceptionsCollector collector;

	public EmfManagementActionPopover(R tableRow) {

		this.entityTable = tableRow.table();
		this.tableRow = tableRow;
		this.collector = new ExceptionsCollector();

		addCssClass(EmfCssClasses.EMF_MANAGEMENT_ACTIONS_POPOVER);
		addMarker(EmfManagementMarker.ACTIONS_POPOVER);

		addManagementActions();
		new ButtonsForChildTableAppender<>(this, entityTable, tableRow, this::close).appendButtons();

		// add error message element if exceptions were suppressed
		if (!collector.isEmpty()) {
			Log.ferror("Caught exception(s) while appending action buttons: %s", collector.getMessage());
			appendChild(new EmfEntityActionColumnHandlerErrorDiv());
		}
	}

	private void addManagementActions() {

		for (IEmfManagementAction<R> action: entityTable.getManagementActions(tableRow)) {
			try {
				if (action.isAvailable(tableRow, CurrentBasicUser.get())) {
					var button = new DomButton()//
						.setClickCallback(() -> {
							close();
							action.handleClick(tableRow);
						})
						.setIcon(action.getIcon())
						.addMarker(new EmfManagementActionMarker(action));

					IDisplayString actionTitle = action.getTitle();
					if (action.getConfirmationMessageSupplier(tableRow).isPresent()) {
						button.setLabel(actionTitle.concat("..."));
						button.setConfirmationMessageSupplier(action.getConfirmationMessageSupplier(tableRow).get());
					} else {
						button.setLabel(actionTitle);
					}
					appendChild(button);
				}
			} catch (Exception exception) {
				collector.add(exception);
			}
		}
	}

	private static class ButtonsForChildTableAppender<R extends IEmfTableRow<R, ?>> {

		private final IDomParentElement parent;
		private final IEmfTable<R, ?, ?> table;
		private final R tableRow;
		private final INullaryVoidFunction callbackAfterShow;

		public ButtonsForChildTableAppender(IDomParentElement parent, IEmfTable<R, ?, ?> table, R tableRow, INullaryVoidFunction callbackAfterShow) {

			this.parent = parent;
			this.table = table;
			this.tableRow = tableRow;
			this.callbackAfterShow = callbackAfterShow;
		}

		public void appendButtons() {

			// add buttons for child tables
			for (IEmfTable<?, ?, R> childTable: table.getChildTables()) {
				addButtonForChildTable(childTable);
			}

			// add buttons for child tables of base
			table.getTableSpecialization().getBase(tableRow).ifPresent(base -> recurse(base));
		}

		private void recurse(IEmfTableRow<?, ?> base) {

			// this cast is only necessary because of a defect in javac 1.8
			doRecurse(CastUtils.cast(base.getThis()));
		}

		private <B extends IEmfTableRow<B, ?>> void doRecurse(B base) {

			IEmfTable<B, ?, ?> baseTable = base.table();
			new ButtonsForChildTableAppender<>(parent, baseTable, base.getThis(), callbackAfterShow).appendButtons();
		}

		private <C extends IEmfTableRow<C, CP>, CP> void addButtonForChildTable(IEmfTable<C, CP, R> childTable) {

			parent
				.appendChild(
					new EmfManagementButton<>(childTable, tableRow)
						.setCallbackAfterShow(callbackAfterShow)
						.setLabel(childTable.getPluralTitle())
						.addMarker(EmfManagementMarker.MANAGE_CHILD_TABLE_BUTTON));
		}
	}

	private static class EmfEntityActionColumnHandlerErrorDiv extends DomDiv {

		public EmfEntityActionColumnHandlerErrorDiv() {

			setStyle(CssFontWeight.BOLD);
			setStyle(CssDisplay.INLINE_BLOCK);
			setColor(CssColorEnum.RED);
			appendText(EmfI18n.ERROR.concat("!"));
			setTitle(EmfI18n.AN_ERROR_OCCURRED_WHILE_ADDING_AN_ACTION);
		}
	}
}
