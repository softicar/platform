package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Transforms I18n formatting arguments according to {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
class I18nFormatArgumentsLocalizer {

	private final Object[] arguments;

	public I18nFormatArgumentsLocalizer(Object[] arguments) {

		this.arguments = Objects.requireNonNull(arguments);
	}

	public Object[] localize() {

		var result = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			result[i] = processArgument(arguments[i]);
		}
		return result;
	}

	private Object processArgument(Object argument) {

		if (argument instanceof BigDecimal) {
			return new BigDecimalFormatter().format((BigDecimal) argument);
		} else if (argument instanceof Double) {
			return new BigDecimalFormatter().format((Double) argument);
		} else if (argument instanceof Float) {
			return new BigDecimalFormatter().format((Float) argument);
		} else {
			return argument;
		}
	}
}
