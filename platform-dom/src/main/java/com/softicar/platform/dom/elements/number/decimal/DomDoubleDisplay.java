package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;

/**
 * Implementation of {@link DomDecimalDisplay} for {@link Double}.
 *
 * @author Oliver Richers
 */
public class DomDoubleDisplay extends DomDecimalDisplay<Double> {

	public DomDoubleDisplay() {

		super(BigDecimalMapper.DOUBLE);
	}
}
