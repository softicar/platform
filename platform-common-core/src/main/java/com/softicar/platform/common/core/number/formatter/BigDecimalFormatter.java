package com.softicar.platform.common.core.number.formatter;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.ILocale;
import java.math.BigDecimal;

/**
 * A formatter for {@link BigDecimal} respecting {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class BigDecimalFormatter {

	private final BigDecimal value;
	private ILocale locale;

	public BigDecimalFormatter(BigDecimal value) {

		this.value = value;
		this.locale = CurrentLocale.get();
	}

	public BigDecimalFormatter setLocale(ILocale locale) {

		this.locale = locale;
		return this;
	}

	public String format() {

		var plainString = value.abs().toPlainString();
		var decimalSeparatorIndex = plainString.indexOf('.');

		if (decimalSeparatorIndex < 0) {
			return formatIntegralPart(plainString);
		} else {
			var integralPart = plainString.substring(0, decimalSeparatorIndex);
			var fractionalPart = plainString.substring(decimalSeparatorIndex + 1);
			return formatIntegralPart(integralPart) + locale.getDecimalSeparator() + fractionalPart;
		}
	}

	private String formatIntegralPart(String integralPart) {

		return getSign() + new DigitGroupFormatter(integralPart, locale.getDigitGroupSeparator()).format();
	}

	private String getSign() {

		return value.signum() < 0? "-" : "";
	}
}
