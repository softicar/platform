package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.table.EmfTableWrapper;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfChildTablesColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfTableWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfTableWrapper> cell, EmfTableWrapper value) {

		DomDiv content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_CHILD_TABLES_COLUMN_HANDLER);
		value//
			.getTable()
			.getChildTables()
			.stream()
			.forEach(child -> appendChildTable(content, child));
		cell.appendChild(content);
	}

	private void appendChildTable(DomDiv content, IEmfTable<?, ?, ?> table) {

		content.appendChild(new EmfTableViewButton(table));
		content.appendNewChild(DomElementTag.BR);
	}
}
