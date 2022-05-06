package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.text.DomVerticalTextBox;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.EmfDataTableFilterButton;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

public class EmfDataTableHeaderCell<R, T> extends DomHeaderCell implements IRefreshable {

	private final IEmfDataTableColumn<R, T> column;

	public EmfDataTableHeaderCell(IEmfDataTableColumn<R, T> column) {

		this.column = column;
		addMarker(EmfDataTableDivMarker.HEADER_PRIMARY_CELL);
		column.getSettings().getMarkers().forEach(this::addMarker);
		refresh();
	}

	public IDataTableColumn<?, ?> getDataColumn() {

		return column.getDataColumn();
	}

	@Override
	public void refresh() {

		removeChildren();

		if (column.getSettings().isVerticalHeader()) {
			appendChild(new VerticalCellDiv());
			addCssClass(EmfCssClasses.EMF_DATA_TABLE_VERTICAL_HEADER_CELL);
		} else {
			appendChild(new CellDiv());
		}
	}

	private class VerticalCellDiv extends DomVerticalTextBox {

		public VerticalCellDiv() {

			addCssClass(EmfCssClasses.EMF_DATA_TABLE_VERTICAL_HEADER_CELL_DIV);
			appendChild(new CellDiv());
		}
	}

	private class CellDiv extends DomDiv {

		public CellDiv() {

			addCssClass(EmfCssClasses.EMF_DATA_TABLE_HEADER_CELL_DIV);
			DomBar bar = new DomBar();
			// caption node or button
			IDomNode captionNode = createCaptionNode();
			captionNode.addMarker(EmfDataTableDivMarker.HEADER_CAPTION);
			bar.appendChild(captionNode);

			// sort buttons
			if (column.isSortable()) {
				bar.appendChild(new EmfDataTableOrderByButton<>(column));
			}
			appendChild(bar);

			// filter node
			IEmfDataTableFilter<?> filter = column.getColumnFilter();
			if (filter != null) {
				DomDiv filterLabel = new DomDiv();
				filterLabel.appendText(filter.getDisplayString());
				appendChild(filterLabel);
			}
		}

		private IDomNode createCaptionNode() {

			if (column.isFilterable()) {
				return new EmfDataTableFilterButton<>(column);
			} else {
				return DomTextNode.create(column.getTitle().toString());
			}
		}
	}
}
