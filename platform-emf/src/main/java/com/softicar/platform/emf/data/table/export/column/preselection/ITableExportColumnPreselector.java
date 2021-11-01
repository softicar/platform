package com.softicar.platform.emf.data.table.export.column.preselection;

import com.softicar.platform.emf.data.table.export.element.TableExportNamedDomCell;
import java.util.Collection;

public interface ITableExportColumnPreselector {

	boolean isPreselected(Collection<TableExportNamedDomCell> cells);
}
