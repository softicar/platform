package com.softicar.platform.emf.attribute.display;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDummyDisplay extends DomDiv {

	public EmfDummyDisplay(IEmfTableRow<?, ?> row) {

		DevNull.swallow(row);
	}
}
