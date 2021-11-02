package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.EmfTableWrapper;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfScopeFieldColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfTableWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfTableWrapper> cell, EmfTableWrapper value) {

		DomDiv content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_SCOPE_FIELD_COLUMN_HANDLER);
		value//
			.getTable()
			.getScopeField()
			.ifPresent(scopeField -> buildCellContent(content, scopeField.getTargetTable()));
		cell.appendChild(content);
	}

	private void buildCellContent(DomDiv content, ISqlTable<?> dbTable) {

		if (dbTable instanceof EmfObjectTable) {
			content.appendChild(new EmfTableViewButton((IEmfTable<?, ?, ?>) dbTable));
		} else {
			content.appendChild(dbTable.getFullName());
		}
	}
}
