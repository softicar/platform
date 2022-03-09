package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import java.math.BigDecimal;

/**
 * Transforms I18n formatting arguments according to {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
class I18nFormatArgumentsProcessor {

	private final Object[] arguments;

	public I18nFormatArgumentsProcessor(Object[] arguments) {

		this.arguments = arguments;
	}

	public Object[] process() {

		var result = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			result[i] = processArgument(arguments[i]);
		}
		return result;
	}

	private Object processArgument(Object argument) {

		if (argument instanceof BigDecimal) {
			return new BigDecimalFormatter((BigDecimal) argument).format();
		} else {
			return argument;
		}
	}
}
