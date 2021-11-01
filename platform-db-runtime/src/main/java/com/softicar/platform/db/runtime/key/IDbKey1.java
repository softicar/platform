package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;

/**
 * Represents a single-field {@link IDbKey}.
 *
 * @author Oliver Richers
 */
public interface IDbKey1<R, V> extends IDbKey<R> {

	/**
	 * Returns the {@link IDbField} of the {@link IDbKey}.
	 *
	 * @return the field (never <i>null</i>)
	 */
	IDbField<R, V> getField();
}
