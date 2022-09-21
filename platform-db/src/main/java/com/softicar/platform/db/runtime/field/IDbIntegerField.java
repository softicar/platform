package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.fields.ISqlIntegerField;

public interface IDbIntegerField<R> extends IDbPrimitiveField<R, Integer>, ISqlIntegerField<R> {

	/**
	 * Returns the number of bits used to encode this {@link Integer} field.
	 * <p>
	 * Valid values are 8, 16, 24 or 32 bits.
	 *
	 * @return the number of bits
	 */
	int getBits();

	/**
	 * Returns whether this field is signed or unsigned.
	 *
	 * @return <i>true</i> if this field is unsigned; <i>false</i> otherwise
	 */
	boolean isUnsigned();
}
