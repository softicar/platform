package com.softicar.platform.emf.data.table.export.button;

import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.IDomPageableTableNavigationButtonBuilder;
import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButtonBuilder;

public class EmfDataTableExportButtonBuilder extends TableExportPopupButtonBuilder implements IDomPageableTableNavigationButtonBuilder {

	public EmfDataTableExportButtonBuilder(DomPageableTable table) {

		addMainTable(table);
		setShowLabel(false);
	}
}
