package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.common.core.number.formatter.BigDecimalScaleApplier;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An {@link IDomElement} to display decimal values according to
 * {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class DomDecimalDisplay<T extends Number> extends DomDiv {

	private final BigDecimalMapper<T> mapper;
	private Integer scale;

	public DomDecimalDisplay(BigDecimalMapper<T> mapper) {

		this.mapper = mapper;
		this.scale = null;
	}

	public DomDecimalDisplay<T> setScale(int scale) {

		this.scale = scale;
		return this;
	}

	public DomDecimalDisplay<T> setValue(T value) {

		removeChildren();

		if (value != null) {
			var decimal = getScaled(mapper.toBigDecimal(value));
			appendText(new BigDecimalFormatter(decimal).format());
		}

		return this;
	}

	private BigDecimal getScaled(BigDecimal value) {

		return new BigDecimalScaleApplier()//
			.setScale(scale)
			.setRoundingMode(RoundingMode.HALF_UP)
			.apply(value);
	}
}
