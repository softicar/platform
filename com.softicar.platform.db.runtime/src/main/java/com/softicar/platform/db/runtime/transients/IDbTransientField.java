package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * This is the interface for non-persistent fields.
 *
 * @param <O>
 *            the type of the object owning this field
 * @param <V>
 *            the type of the field values
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IDbTransientField<O, V> {

	V getValue(O object);

	/**
	 * Returns the title of this field.
	 *
	 * @return the field title (never <i>null</i>)
	 */
	IDisplayString getTitle();
}
