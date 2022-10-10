package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.math.BigDecimal;

public class EmfBigDecimalInput extends DomBigDecimalInput implements IEmfInput<BigDecimal> {

	@Override
	public IEmfInput<BigDecimal> appendLabel(IDisplayString label) {

		input.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
