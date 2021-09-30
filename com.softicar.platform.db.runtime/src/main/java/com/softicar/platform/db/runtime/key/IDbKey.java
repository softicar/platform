package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.List;

/**
 * Represents a key of a database table;
 *
 * @param <R>
 *            the type of the table rows
 * @author Oliver Richers
 */
public interface IDbKey<R> {

	/**
	 * Returns the {@link DbKeyType} of this key.
	 *
	 * @return the key type (never <i>null</i>)
	 */
	DbKeyType getType();

	/**
	 * Returns whether this is the primary key of the table or not.
	 *
	 * @return <i>true</i> if this is the primary key; <i>false</i> otherwise
	 */
	boolean isPrimaryKey();

	/**
	 * Returns whether this is a unique key.
	 * <p>
	 * All primary keys are considered to be unique keys, that is,
	 * {@link #isPrimaryKey()} implies {@link #isUniqueKey()}.
	 *
	 * @return <i>true</i> if this is a unique key; <i>false</i> otherwise
	 */
	boolean isUniqueKey();

	/**
	 * Returns the name of this key.
	 *
	 * @return the name of this key (may be null)
	 */
	String getName();

	/**
	 * Returns all table fields making up this key.
	 *
	 * @return a list of all keys (never null and never empty)
	 */
	List<IDbField<R, ?>> getFields();
}
