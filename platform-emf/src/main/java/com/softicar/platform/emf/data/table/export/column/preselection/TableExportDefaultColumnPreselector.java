package com.softicar.platform.emf.data.table.export.column.preselection;

import com.softicar.platform.dom.elements.cell.separator.IDomSeparatorCell;
import com.softicar.platform.emf.data.table.export.element.TableExportNamedDomCell;
import java.util.Collection;

public class TableExportDefaultColumnPreselector implements ITableExportColumnPreselector {

	@Override
	public boolean isPreselected(Collection<TableExportNamedDomCell> cells) {

		boolean preselected = true;

		for (TableExportNamedDomCell namedCell: cells) {
			if (namedCell != null && namedCell.getCell() instanceof IDomSeparatorCell) {
				preselected = false;
			}
		}

		return preselected;
	}
}
