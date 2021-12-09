package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbByteArrayField;
import com.softicar.platform.emf.EmfI18n;
import java.util.Base64;

class EmfTokenToByteArrayConverter extends AbstractEmfTokenConverter<byte[], DbByteArrayField<?>> {

	@Override
	public EmfTokenConverterResult<byte[]> convertToken(DbByteArrayField<?> targetField, String token) {

		byte[] value = Base64.getDecoder().decode(token);
		return EmfTokenConverterResult.okay(value);
	}

	@Override
	public IDisplayString getTypeDescription() {

		return EmfI18n.BASE64_ENCODED_BINARY_DATA;
	}
}
