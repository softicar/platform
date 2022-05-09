package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.building.EmfDataTableCellDefaultsProvider;
import java.util.Collections;
import java.util.Objects;

class EmfDataTableRow<R> extends DomRow implements IEmfDataTableRow<R> {

	private final IEmfDataTable<R> dataTable;
	private final R dataRow;
	private final EmfDataTableRowSelectionCheckbox<R> selectionCheckbox;
	private IEmfDataTableRowEventHandler<R> eventHandler;

	public EmfDataTableRow(IEmfDataTable<R> dataTable, R dataRow) {

		this.dataTable = dataTable;
		this.dataRow = dataRow;
		this.selectionCheckbox = dataTable.getConfig().isRowSelectionEnabled()? new EmfDataTableRowSelectionCheckbox<>(this) : null;
		this.eventHandler = DevNull::swallow;
		addMarker(EmfDataTableDivMarker.BODY_ROW);
		customize();
		appendCells();
	}

	@Override
	public void refresh() {

		removeChildren();
		dataTable.prefetchData(Collections.singleton(dataRow));
		appendCells();
	}

	@Override
	public void showAsSelected(boolean selected) {

		selectionCheckbox.setValue(selected);

		if (selected) {
			addCssClass(DomCssPseudoClasses.SELECTED);
		} else {
			removeCssClass(DomCssPseudoClasses.SELECTED);
			customize();
		}
	}

	@Override
	public void setSelected(boolean doSelect, boolean doCallbacks) {

		dataTable.setRowSelected(this, doSelect, doCallbacks);
	}

	@Override
	public boolean isSelected() {

		return dataTable.getController().isRowSelected(this);
	}

	@Override
	public IEmfDataTable<R> getTable() {

		return dataTable;
	}

	@Override
	public R getDataRow() {

		return dataRow;
	}

	@Override
	public void handleDOMEvent(IDomEvent event) {

		eventHandler.handleEvent(getDataRow());
	}

	@Override
	public void setEventHandler(IEmfDataTableRowEventHandler<R> eventHandler) {

		this.eventHandler = Objects.requireNonNull(eventHandler);
	}

	private void customize() {

		dataTable//
			.getConfig()
			.getRowCustomizer()
			.ifPresent(it -> it.customizeRow(this));
	}

	private void appendCells() {

		if (dataTable.getConfig().isRowSelectionEnabled() || dataTable.getConfig().getActionColumnHandler().isPresent()) {
			appendChild(new ActionCell());
		}

		for (IEmfDataTableColumn<R, ?> column: dataTable.getColumnsOrderedByCustomIndex()) {
			if (!column.isConcealed() && !column.isHidden()) {
				appendChild(createCell(column, dataRow));
			}
		}
	}

	private <T> DomCell createCell(IEmfDataTableColumn<R, T> column, R dataRow) {

		EmfDataTableCell<R, T> cell = new EmfDataTableCell<>(this, column);
		if (!column.isHidden()) {
			setDefaultTextAlign(column, cell);
			dataTable.getConfig().getColumnHandler(column.getDataColumn()).buildCell(cell, dataRow);
		}
		return cell;
	}

	private <T> void setDefaultTextAlign(IEmfDataTableColumn<R, T> column, EmfDataTableCell<R, T> cell) {

		CssTextAlign textAlign = EmfDataTableCellDefaultsProvider.get().getTextAlign(column.getDataColumn());
		cell.setStyle(cell.getColumn().getSettings().getAlignmentOrDefault(textAlign));
	}

	private class ActionCell extends DomCell implements IEmfDataTableActionCell<R> {

		private final DomBar container;

		public ActionCell() {

			this.container = appendChild(new DomBar());
			if (dataTable.getConfig().isRowSelectionEnabled()) {
				container.appendChild(selectionCheckbox);
			}
			dataTable//
				.getConfig()
				.getActionColumnHandler()
				.ifPresent(it -> it.buildCell(this, dataRow));
			addMarker(EmfDataTableDivMarker.ACTION_CELL);
		}

		@Override
		public IEmfDataTableRow<R> getTableRow() {

			return EmfDataTableRow.this;
		}

		@Override
		public IDomParentElement getContentContainer() {

			return container;
		}
	}
}
