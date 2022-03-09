package com.softicar.platform.emf.attribute.field.bigdecimal;

import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.dom.elements.DomDiv;
import java.math.BigDecimal;

public class EmfBigDecimalDisplay extends DomDiv {

	public EmfBigDecimalDisplay(BigDecimal value) {

		appendText(new BigDecimalFormatter(value).format());
	}
}
