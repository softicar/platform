package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbByteArrayField;
import com.softicar.platform.emf.EmfI18n;
import java.util.Base64;
import java.util.Optional;

class EmfTokenToByteArrayConverter extends AbstractEmfTokenConverter<byte[], DbByteArrayField<?>> {

	@Override
	public EmfTokenConverterResult<byte[]> convertToken(DbByteArrayField<?> targetField, String token) {

		byte[] value = Base64.getDecoder().decode(token);

		var maximumLengthResult = checkSize(value.length, targetField.getMaximumLength());
		if (maximumLengthResult.isPresent()) {
			return maximumLengthResult.get();
		}

		var lengthBitsResult = checkSize(value.length, determineMaximumSize(targetField.getLengthBits()));
		if (lengthBitsResult.isPresent()) {
			return lengthBitsResult.get();
		}

		return EmfTokenConverterResult.okay(value);
	}

	@Override
	public IDisplayString getTypeDescription() {

		return EmfI18n.BASE64_ENCODED_BINARY_DATA;
	}

	private Optional<EmfTokenConverterResult<byte[]>> checkSize(int encounteredLength, long maximumLength) {

		if (maximumLength > 0) {
			if (encounteredLength > maximumLength) {
				IDisplayString message = EmfI18n.TOO_MANY_BYTES
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
