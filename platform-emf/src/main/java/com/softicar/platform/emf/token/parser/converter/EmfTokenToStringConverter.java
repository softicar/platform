package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbStringField;
import com.softicar.platform.emf.EmfI18n;
import java.util.Optional;

class EmfTokenToStringConverter extends AbstractEmfTokenConverter<String, DbStringField<?>> {

	private static final String MAGIC_CONSTANT_FOR_UNDEFINED = "NULL";

	/**
	 * This converter returns an empty string value if an empty token is given.
	 * In contrast, all other converters return a <i>null</i> value if an empty
	 * token is given.
	 * <p>
	 * The only way to let this converter return a <i>null</i> value is, to pass
	 * a token that literally matches the string
	 * {@value #MAGIC_CONSTANT_FOR_UNDEFINED}.
	 * <p>
	 * This means that this converter will never return the literal value
	 * {@value #MAGIC_CONSTANT_FOR_UNDEFINED}.
	 */
	@Override
	public EmfTokenConverterResult<String> convertToken(DbStringField<?> targetField, String token) {

		if (isUndefined(token)) {
			return EmfTokenConverterResult.okay(null);
		} else {
			var maximumLengthResult = checkSize(token.length(), targetField.getMaximumLength());
			if (maximumLengthResult.isPresent()) {
				return maximumLengthResult.get();
			}

			var lengthBitsResult = checkSize(token.length(), determineMaximumSize(targetField.getLengthBits()));
			if (lengthBitsResult.isPresent()) {
				return lengthBitsResult.get();
			}

			return EmfTokenConverterResult.okay(token);
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return EmfI18n.TEXT;
	}

	@Override
	protected boolean isUndefined(String token) {

		return token.equals(MAGIC_CONSTANT_FOR_UNDEFINED);
	}

	private Optional<EmfTokenConverterResult<String>> checkSize(int encounteredLength, long maximumLength) {

		if (maximumLength > 0) {
			if (encounteredLength > maximumLength) {
				IDisplayString message = EmfI18n.TOO_MANY_CHARACTERS
					.concatSpace()
					.concatInParentheses(EmfI18n.MAXIMUM_ARG1_ENCOUNTERED_ARG2.toDisplay(maximumLength, encounteredLength));
				return Optional.of(EmfTokenConverterResult.failed(message));
			}
		}
		return Optional.empty();
	}

	private long determineMaximumSize(int lengthBits) {

		if (lengthBits > 0) {
			switch (lengthBits) {
			case 8:
				return 255;
			case 16:
				return 65535;
			case 24:
				return 16777215;
			case 32:
				return 4294967295l;
			}
		}
		return 0;
	}
}
