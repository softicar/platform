package com.softicar.platform.emf.table.row;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfTableRowDisplay<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfTableRowDisplay(R row) {

		if (row != null) {
			appendText(row.toDisplay());
		}
	}
}
