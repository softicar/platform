package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.input.AbstractDomNumberInput;
import com.softicar.platform.dom.input.DomTextInput;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link BigDecimal} value.
 *
 * @author Semsudin Mekanic
 */
public class DomBigDecimalInput extends AbstractDomNumberInput {

	private final static int DEFAULT_SCALE = 9;
	private final static RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	private int scale = DEFAULT_SCALE;

	public DomBigDecimalInput() {

		this(null);
	}

	public DomBigDecimalInput(BigDecimal value) {

		setBigDecimal(value);
	}

	public DomBigDecimalInput(BigDecimal value, int scale) {

		setBigDecimal(value, scale);
	}

	public void setBigDecimal(BigDecimal value) {

		if (value != null) {
			setValue(replacePointWithComma(value.toPlainString()));
			this.scale = value.scale();
		} else {
			setValue(null);
			this.scale = DEFAULT_SCALE;
		}
	}

	public void setBigDecimal(BigDecimal value, int scale) {

		if (value != null) {
			value = value.setScale(scale, DEFAULT_ROUNDING_MODE);
		}
		setBigDecimal(value);
		this.scale = scale;
	}

	public BigDecimal getBigDecimalOrNull() {

		return getBigDecimalOrNull(false);
	}

	public BigDecimal getBigDecimalOrZero() {

		BigDecimal value = getBigDecimalOrNull(false);
		return value != null? value : BigDecimal.ZERO.setScale(scale);
	}

	public BigDecimal getBigDecimalOrNull(boolean stripTrailingZeros) {

		String value = getTextOrNull();
		if (value != null) {
			try {
				value = replaceCommaWithPoint(value);
				if (stripTrailingZeros) {
					return new BigDecimal(value).setScale(scale, DEFAULT_ROUNDING_MODE).stripTrailingZeros();
				} else {
					return new BigDecimal(value).setScale(scale, DEFAULT_ROUNDING_MODE);
				}
			} catch (NumberFormatException exception) {
				DevNull.swallow(exception);
				return null;
			}
		}
		return null;
	}

	private String replacePointWithComma(String value) {

		return value != null? value.replace(".", ",") : value;
	}

	private String replaceCommaWithPoint(String value) {

		return value != null? value.replace(",", ".") : value;
	}

	public int getScale() {

		return scale;
	}
}
