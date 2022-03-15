package com.softicar.platform.emf.attribute.transients;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalDisplay;
import com.softicar.platform.dom.elements.number.decimal.DomDoubleDisplay;
import com.softicar.platform.dom.elements.number.decimal.DomFloatDisplay;
import java.math.BigDecimal;

public class EmfTransientAttributeDisplay<V> extends DomDiv {

	public EmfTransientAttributeDisplay(V value) {

		if (value != null) {
			// TODO this needs to be centralized
			if (value instanceof BigDecimal) {
				appendChild(new DomBigDecimalDisplay().setValue((BigDecimal) value));
			} else if (value instanceof Double) {
				appendChild(new DomDoubleDisplay().setValue((Double) value));
			} else if (value instanceof Float) {
				appendChild(new DomFloatDisplay().setValue((Float) value));
			} else {
				appendText(value.toString());
			}
		}
	}
}
