package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.fields.ISqlLongField;

public interface IDbLongField<R> extends IDbPrimitiveField<R, Long>, ISqlLongField<R> {

	/**
	 * Returns the number of bits used to encode this {@link Long} field.
	 * <p>
	 * Valid values are 8, 16, 24, 32 or 64 bits.
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
