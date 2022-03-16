package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import java.math.BigDecimal;

/**
 * A {@link DomDecimalInput} for {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public class DomBigDecimalInput extends DomDecimalInput<BigDecimal> {

	public DomBigDecimalInput() {

		super(BigDecimalMapper.BIG_DECIMAL);
	}

	public DomBigDecimalInput(BigDecimal value) {

		super(BigDecimalMapper.BIG_DECIMAL);
		setValue(value);
	}
}
