package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;

/**
 * Interface of value converters that turn a {@link String} based token into an
 * object, according to the value type of a specified {@link IDbField}.
 *
 * @author Alexander Schmidt
 */
interface IEmfTokenConverter<V, F extends IDbField<?, ?>> {

	/**
	 * Converts the given token to the value type of the given {@link IDbField}.
	 * <p>
	 * The result of the conversion is wrapped in a
	 * {@link EmfTokenConverterResult}.
	 * <p>
	 * If an {@link Exception} is encountered during conversion, it must
	 * <b>not</b> be thrown. Instead, the {@link Exception} must be wrapped via
	 * {@link EmfTokenConverterResult#failed(Exception, IDisplayString)}.
	 *
	 * @param field
	 *            the field that specifies the target type (never <i>null</i>)
	 * @param token
	 *            the {@link String} token to convert (never <i>null</i>)
	 * @return the {@link EmfTokenConverterResult} (never <i>null</i>)
	 */
	EmfTokenConverterResult<V> convert(F field, String token);

	/**
	 * A human-readable description of the target value type.
	 * <p>
	 * May contain examples, like "Integer (e.g. 128)".
	 *
	 * @return the description of the target value type (never <i>null</i>)
	 */
	IDisplayString getTypeDescription();
}
