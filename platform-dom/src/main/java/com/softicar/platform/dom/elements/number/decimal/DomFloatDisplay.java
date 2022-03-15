package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;

/**
 * Implementation of {@link DomDecimalDisplay} for {@link Float}.
 *
 * @author Oliver Richers
 */
public class DomFloatDisplay extends DomDecimalDisplay<Float> {

	public DomFloatDisplay() {

		super(BigDecimalMapper.FLOAT);
	}
}
