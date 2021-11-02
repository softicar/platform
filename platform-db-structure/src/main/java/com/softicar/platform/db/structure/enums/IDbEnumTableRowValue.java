package com.softicar.platform.db.structure.enums;

/**
 * Represents a value in a column of an {@link IDbEnumTableRowStructure}.
 *
 * @author Oliver Richers
 */
public interface IDbEnumTableRowValue {

	/**
	 * Converts this value using the given converter, that is, calls the
	 * respective respective convert method on it.
	 *
	 * @param converter
	 *            the converter (never null)
	 * @return the converted value (may be null)
	 */
	<T> T convert(IDbEnumTableRowValueConverter<T> converter);

	/**
	 * Converts the given untyped value into an {@link IDbEnumTableRowValue}.
	 *
	 * @param untypedValue
	 *            the value to convert (may be null)
	 * @return the respective instance of {@link IDbEnumTableRowValue}
	 * @throws DbIllegalEnumTableRowValueFieldTypeException
	 *             if the given untyped value cannot be converted
	 */
	static IDbEnumTableRowValue valueOf(Object untypedValue) {

		return new InternalDbEnumTableRowValueFromUntypedValueFactory(untypedValue).create();
	}
}
