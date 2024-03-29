package com.softicar.platform.common.core.number.formatter;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.ILocale;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A formatter for {@link BigDecimal} respecting {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class BigDecimalFormatter {

	private static final int DIGIT_GROUP_SIZE = 3;
	private ILocale locale;
	private boolean digitGroupSeparation;

	public BigDecimalFormatter() {

		this.locale = CurrentLocale.get();
		this.digitGroupSeparation = true;
	}

	/**
	 * Overrides the {@link ILocale} to use for formatting.
	 *
	 * @param locale
	 *            the {@link ILocale} to use (never <i>null</i>)
	 * @return this
	 */
	public BigDecimalFormatter setLocale(ILocale locale) {

		this.locale = Objects.requireNonNull(locale);
		return this;
	}

	/**
	 * If enabled, digit group separators will be inserted into the integral
	 * number part where appropriate.
	 * <p>
	 * By default, this is enabled, i.e. digit group separators are added.
	 *
	 * @param digitGroupSeparation
	 *            <i>true</i> to enable insertion of digit group separators;
	 *            <i>false</i> to disable it
	 * @return this
	 */
	public BigDecimalFormatter setApplyDigitGroupSeparation(boolean digitGroupSeparation) {

		this.digitGroupSeparation = digitGroupSeparation;
		return this;
	}

	/**
	 * Returns the number of digits per digit group.
	 *
	 * @return the number of digits
	 */
	public static int getDigitGroupSize() {

		return DIGIT_GROUP_SIZE;
	}

	public String format(BigDecimal value) {

		var sign = getSign(value);
		var plainString = value.abs().toPlainString();
		var decimalSeparatorIndex = plainString.indexOf('.');

		if (decimalSeparatorIndex < 0) {
			return sign + formatIntegralPart(plainString);
		} else {
			var integralPart = plainString.substring(0, decimalSeparatorIndex);
			var fractionalPart = plainString.substring(decimalSeparatorIndex + 1);
			return sign + formatIntegralPart(integralPart) + locale.getDecimalSeparator() + fractionalPart;
		}
	}

	public String format(Double value) {

		return format(BigDecimal.valueOf(value));
	}

	public String format(Float value) {

		return format(BigDecimal.valueOf(value));
	}

	private String formatIntegralPart(String integralPart) {

		if (digitGroupSeparation) {
			return new DigitGroupFormatter(integralPart, locale.getDigitGroupSeparator()).format();
		} else {
			return integralPart;
		}
	}

	private static String getSign(BigDecimal value) {

		return value.signum() < 0? "-" : "";
	}
}
