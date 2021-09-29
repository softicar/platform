package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.data.table.EmfAttributeColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfBooleanAttributeColumnHandler<R extends IEmfTableRow<R, ?>> extends EmfAttributeColumnHandler<R, Boolean> {

	public EmfBooleanAttributeColumnHandler(IEmfAttribute<R, Boolean> attribute) {

		super(attribute);
	}

	@Override
	public void buildCell(IEmfDataTableCell<R, Boolean> cell, R tableRow) {

		super.buildCell(cell, tableRow);

		cell.setStyle(CssTextAlign.CENTER);
	}
}
