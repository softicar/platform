package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.dom.elements.number.decimal.DomDecimalInput;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Float} values.
 *
 * @author Oliver Richers
 */
public class DomFloatInput extends DomDecimalInput<Float> {

	public DomFloatInput() {

		super(BigDecimalMapper.FLOAT);
	}
}
