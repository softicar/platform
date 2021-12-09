package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.emf.EmfI18n;

abstract class AbstractEmfTokenConverter<V, F extends IDbField<?, ?>> implements IEmfTokenConverter<V, F> {

	@Override
	public final EmfTokenConverterResult<V> convert(F field, String token) {

		if (isUndefined(token)) {
			if (isId(field) || field.isNullable()) {
				return EmfTokenConverterResult.okay(null);
			} else {
				return EmfTokenConverterResult.failed(EmfI18n.A_VALUE_OF_TYPE_ARG1_IS_MISSING.toDisplay(getTypeDescription()));
			}
		} else {
			try {
				return convertToken(field, token);
			} catch (Exception exception) {
				return EmfTokenConverterResult.failed(exception, EmfI18n.ARG1_IS_NOT_OF_TYPE_ARG2.toDisplay(token, getTypeDescription()));
			}
		}
	}

	protected abstract EmfTokenConverterResult<V> convertToken(F field, String token) throws Exception;

	protected boolean isUndefined(String token) {

		return token.isEmpty();
	}

	protected IDisplayString createTypeDescriptionWithExample(IDisplayString typeName, String example) {

		return typeName//
			.concatSpace()
			.concatInParentheses(EmfI18n.E_G.concatSpace().concat(example));
	}

	protected IDisplayString createValueOutOfRangeMessage(String token, String minValue, String maxValue) {

		return EmfI18n.VALUE_ARG1_IS_OUT_OF_RANGE.toDisplay(token).concatSpace().concat("[" + minValue + " ... " + maxValue + "]");
	}

	private boolean isId(F field) {

		return IDbIdField.class.isInstance(field);
	}
}
