package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;

/**
 * Represents an {@link IDbKey} with three fields.
 *
 * @author Oliver Richers
 */
public interface IDbKey3<R, V0, V1, V2> extends IDbKey<R> {

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

	/**
	 * Returns the third {@link IDbField} of the {@link IDbKey}.
	 *
	 * @return the third field (never <i>null</i>)
	 */
	IDbField<R, V2> getField2();
}
