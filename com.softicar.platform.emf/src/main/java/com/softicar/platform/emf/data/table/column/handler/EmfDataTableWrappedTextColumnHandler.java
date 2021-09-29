package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

public class EmfDataTableWrappedTextColumnHandler extends EmfDataTableValueBasedColumnHandler<String> {

	private final String rowSplitter;

	public EmfDataTableWrappedTextColumnHandler(String rowSplitter) {

		this.rowSplitter = rowSplitter;
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, String> cell, String value) {

		if (value != null) {
			String[] lines = value.split(rowSplitter);
			for (String line: lines) {
				cell.appendChild(new DomPreformattedLabel(line));
				cell.appendNewChild(DomElementTag.BR);
			}
		}
	}
}
