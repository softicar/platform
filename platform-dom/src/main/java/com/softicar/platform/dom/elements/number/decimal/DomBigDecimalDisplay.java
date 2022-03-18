package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import java.math.BigDecimal;

/**
 * Implementation of {@link DomDecimalDisplay} for {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public class DomBigDecimalDisplay extends DomDecimalDisplay<BigDecimal> {

	public DomBigDecimalDisplay() {

		super(BigDecimalMapper.BIG_DECIMAL);
	}
}
