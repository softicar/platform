package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.logic.DbTableRowResultSetReader;

/**
 * This class enumerates all available status flags of {@link IDbTableRow}.
 * <p>
 * The status of a table row has the following invariants:
 * <ul>
 * <li>When the {@link #STUB} flag is <i>true</i>, all other flags are
 * <i>false</i>.</li>
 * <li>When the {@link #DATA_CHANGED} flag is <i>true</i>, the {@link #DIRTY}
 * flag is also <i>true</i>.</li>
 * </ul>
 *
 * @author Oliver Richers
 */
public enum DbTableRowFlag {

	/**
	 * Indicates that a table row does not exists in the database.
	 * <p>
	 * If this flag is true, either the table row was created in memory but not
	 * saved to the database table yet, or the table row was deleted.
	 * <p>
	 * This flag does <b>not</b> indicate if there are any impermanent changes
	 * to the table row. Use the {@link #DATA_CHANGED} flag for that.
	 */
	IMPERMANENT,

	/**
	 * Indicates that the value of a data field was changed.
	 * <p>
	 * In contrast to {@link #DIRTY}, this flag is only enabled if the call to
	 * the setter method actually changed the value of the field.
	 *
	 * @see IDbBasicTableRow#dataChanged()
	 * @see IDbBasicTableRow#dataChanged(IDbField)
	 */
	DATA_CHANGED,

	/**
	 * Indicates that a setter on the field of the table row was called.
	 * <p>
	 * In contrast to {@link #DATA_CHANGED} it does <b>not</b> matter if the
	 * call to the setter method actually changed the value of a field.
	 *
	 * @see IDbBasicTableRow#dirty()
	 */
	DIRTY,

	/**
	 * Indicates that the table row was invalidated, i.e. is deemed to be
	 * reloaded on next access.
	 *
	 * @see IDbBasicTableRow#invalidated()
	 */
	INVALIDATED,

	/**
	 * Indicates that the table row is a stub, i.e. its data was not yet loaded
	 * from the database.
	 * <ul>
	 * <li>Trying to delete or save a stub raises an exception.</li>
	 * <li>Reading or writing a data field of a stub causes the loading of the
	 * stub data, that is, the table row is un-stubbed.</li>
	 * <li>Implicit loading of a stub through {@link DbTableRowResultSetReader}
	 * automatically un-stubs a table row.</li>
	 * </ul>
	 *
	 * @see IDbBasicTableRow#stub()
	 */
	STUB,
	//
	;
}
