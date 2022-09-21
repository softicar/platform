package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;

/**
 * Represents an {@link IDbKey} with two fields.
 *
 * @author Oliver Richers
 */
public interface IDbKey2<R, V0, V1> extends IDbKey<R> {

	/**
	 * Returns the first {@link IDbField} of the {@link IDbKey}.
	 *
	 * @return the first field (never <i>null</i>)
	 */
	IDbField<R, V0> getField0();

	/**
	 * Returns the second {@link IDbField} of the {@link IDbKey}.
	 *
	 * @return the second field (never <i>null</i>)
	 */
	IDbField<R, V1> getField1();
}
