package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.dom.elements.number.decimal.DomDecimalInput;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Double} values.
 *
 * @author Oliver Richers
 */
public class DomDoubleInput extends DomDecimalInput<Double> {

	public DomDoubleInput() {

		super(BigDecimalMapper.DOUBLE);
	}
}
