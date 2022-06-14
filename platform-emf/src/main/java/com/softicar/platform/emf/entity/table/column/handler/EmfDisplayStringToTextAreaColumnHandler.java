package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class EmfDisplayStringToTextAreaColumnHandler extends EmfDataTableValueBasedColumnHandler<IDisplayString> {

	@Override
	public void buildCell(IEmfDataTableCell<?, IDisplayString> cell, IDisplayString value) {

		var content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_DISPLAY_STRING_TO_TEXT_AREA_COLUMN_HANDLER);
		var textArea = new DomTextArea(value.toString()).setSize(3, 40);
		textArea.setDisabled(true);
		content.appendChild(textArea);
		cell.appendChild(content);
	}
}
