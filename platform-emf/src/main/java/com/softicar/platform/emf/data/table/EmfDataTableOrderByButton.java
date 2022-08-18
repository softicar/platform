package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.Optional;

public class EmfDataTableOrderByButton<R> extends DomButton {

	private final IEmfDataTableColumn<R, ?> column;
	private Optional<OrderDirection> direction;
	private EmfDataTableOrderByButtonCountElement countElement;

	public EmfDataTableOrderByButton(IEmfDataTableColumn<R, ?> column) {

		this.column = column;
		this.direction = column.getOrderByDirection();
		this.countElement = null;
		addMarker(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON);
		setClickCallback(this::changeOrder);
		refresh();
	}

	public void refresh() {

		if (direction.isEmpty()) {
			setIcon(EmfImages.TABLE_COLUMN_SORT_NONE.getResource());
		} else if (direction.get() == OrderDirection.ASCENDING) {
			setIcon(EmfImages.TABLE_COLUMN_SORT_ASCENDING.getResource());
		} else {
			setIcon(EmfImages.TABLE_COLUMN_SORT_DESCENDING.getResource());
		}
		setTitle(getTooltip().concat(" ").concat(EmfDataTableI18n.HOLD_DOWN_CONTROL_KEY_FOR_MULTI_COLUMN_SORTING));
		refreshCountElement();
	}

	private IDisplayString getTooltip() {

		if (direction.isEmpty()) {
			return EmfDataTableI18n.NO_SORTING_OF_COLUMN_ARG1.toDisplay(column.getTitle());
		} else if (direction.get() == OrderDirection.ASCENDING) {
			return EmfDataTableI18n.SORT_VALUES_OF_COLUMN_ARG1_IN_ASCENDING_ORDER.toDisplay(column.getTitle());
		} else {
			return EmfDataTableI18n.SORT_VALUES_OF_COLUMN_ARG1_IN_DESCENDING_ORDER.toDisplay(column.getTitle());
		}
	}

	private void changeOrder() {

		if (direction.isEmpty()) {
			this.direction = Optional.of(OrderDirection.ASCENDING);
		} else if (direction.get() == OrderDirection.ASCENDING) {
			this.direction = Optional.of(OrderDirection.DESCENDING);
		} else {
			this.direction = Optional.empty();

		}
		refresh();
		if (direction.isPresent()) {
			if (CurrentDomDocument.get().getCurrentEvent().isCtrlKey()) {
				column.addOrderByThisColumn(direction.get());
			} else {
				column.setOrderByThisColumn(direction.get());
			}
		} else {
			column.removeOrderByThisColumn();
		}
		column.getController().savePersistentTableConfiguration();
	}

	private void refreshCountElement() {

		if (countElement != null) {
			countElement.disappend();
			this.countElement = null;
		}

		EmfDataTableOrdering<R> ordering = column.getController().getOrdering();
		if (direction.isPresent() && ordering.getColumnCount() > 1) {
			Optional<Integer> columnIndex = ordering.getColumnIndex(column.getDataColumn());
			this.countElement = new EmfDataTableOrderByButtonCountElement(1 + columnIndex.orElse(0));
			appendChild(countElement);
		}
	}

	private class EmfDataTableOrderByButtonCountElement extends DomDiv {

		public EmfDataTableOrderByButtonCountElement(int count) {

			appendText("" + count);
			addCssClass(EmfCssClasses.EMF_DATA_TABLE_ORDER_BY_BUTTON_COUNT_ELEMENT);
		}
	}
}
