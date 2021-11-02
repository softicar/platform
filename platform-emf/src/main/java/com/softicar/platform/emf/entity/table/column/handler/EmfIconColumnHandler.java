package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class EmfIconColumnHandler extends EmfDataTableValueBasedColumnHandler<IResource> {

	@Override
	public void buildCell(IEmfDataTableCell<?, IResource> cell, IResource value) {

		DomDiv content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_ICON_COLUMN_HANDLER);
		content.appendChild(new DomImage(value));
		cell.appendChild(content);
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, IResource> column) {

		return false;
	}
}
