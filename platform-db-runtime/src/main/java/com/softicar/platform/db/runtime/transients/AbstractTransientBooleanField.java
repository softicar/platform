package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Defines a transient field with values of type {@link Boolean}.
 * <p>
 * Please note that null-values are not supported. If the values are optional,
 * use {@link AbstractTransientOptionalField}.
 *
 * @param <O>
 *            the type of the field owning object class
 * @author Oliver Richers
 */
public abstract class AbstractTransientBooleanField<O extends IDbTableRow<O, ?>> extends AbstractTransientObjectField<O, Boolean> {

	public AbstractTransientBooleanField() {

		super(TransientFieldValueTypes.getBoolean());
	}
}
