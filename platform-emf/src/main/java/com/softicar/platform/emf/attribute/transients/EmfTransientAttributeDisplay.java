package com.softicar.platform.emf.attribute.transients;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.dom.elements.DomDiv;
import java.math.BigDecimal;

public class EmfTransientAttributeDisplay<V> extends DomDiv {

	public EmfTransientAttributeDisplay(V value) {

		if (value != null) {
			if (value instanceof BigDecimal) {
				appendText(new BigDecimalFormatter().format((BigDecimal) value));
			} else if (value instanceof Double) {
				appendText(new BigDecimalFormatter().format((Double) value));
			} else if (value instanceof Float) {
				appendText(new BigDecimalFormatter().format((Float) value));
			} else if (value instanceof IDisplayable) {
				appendText(((IDisplayable) value).toDisplay());
			} else {
				appendText(value.toString());
			}
		}
	}
}
