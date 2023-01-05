package com.softicar.platform.emf.data.table.export.column.preselection;

import com.softicar.platform.dom.elements.cell.separator.DomSeparatorHeaderCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionHeaderCell;
import com.softicar.platform.emf.data.table.export.element.TableExportNamedDomCell;
import java.util.Collection;

public class TableExportDefaultColumnPreselector implements ITableExportColumnPreselector {

	@Override
	public boolean isPreselected(Collection<TableExportNamedDomCell> namedCells) {

		if (containsAnySeparatorHeaderCell(namedCells)) {
			return false;
		}

		if (containsAnyActionHeaderCell(namedCells)) {
			return false;
		}

		return true;
	}

	private boolean containsAnyActionHeaderCell(Collection<TableExportNamedDomCell> namedCells) {

		return namedCells//
			.stream()
			.map(TableExportNamedDomCell::getCell)
			.anyMatch(IEmfDataTableActionHeaderCell.class::isInstance);
	}

	private boolean containsAnySeparatorHeaderCell(Collection<TableExportNamedDomCell> namedCells) {

		return namedCells//
			.stream()
			.map(TableExportNamedDomCell::getCell)
			.anyMatch(DomSeparatorHeaderCell.class::isInstance);
	}
}
