package com.softicar.platform.db.structure.enums;

import java.math.BigDecimal;

/**
 * Converter interface for {@link IDbEnumTableRowValue}.
 *
 * @author Oliver Richers
 */
public interface IDbEnumTableRowValueConverter<T> {

	/**
	 * Converts the given {@link BigDecimal} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertBigDecimal(BigDecimal value);

	/**
	 * Converts the given {@link Boolean} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertBoolean(Boolean value);

	/**
	 * Converts the given {@link Double} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertDouble(Double value);

	/**
	 * Converts the given {@link Float} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertFloat(Float value);

	/**
	 * Converts the given {@link Integer} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertInteger(Integer value);

	/**
	 * Converts the given {@link Long} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertLong(Long value);

	/**
	 * Converts the given {@link String} value.
	 *
	 * @param value
	 *            the value (may be null)
	 * @return the converted value (may be null)
	 */
	T convertString(String value);

	/**
	 * Converts the <i>null</i> value.
	 *
	 * @return the converted value (may be null)
	 */
	T convertNull();
}
