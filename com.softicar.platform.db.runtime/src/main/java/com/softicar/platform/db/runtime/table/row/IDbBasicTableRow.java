package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.core.table.IDbCoreTableRow;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.IDbBasicTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.statement.SqlSelectLock;

public interface IDbBasicTableRow<R extends IDbBasicTableRow<R>> extends IDbCoreTableRow {

	/**
	 * Returns the table which holds this table row.
	 *
	 * @return the table (never null)
	 */
	IDbBasicTable<R> table();

	/**
	 * Initializes this table row if it was not initialized yet.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 */
	void initializeLazily();

	// ------------------------------ flags ------------------------------ //

	/**
	 * Returns an array holing the status flags of this element.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @return the status flags (never null)
	 */
	byte[] flags();

	/**
	 * Returns whether this is a fully loaded instance or only a stub.
	 *
	 * @return true if this is only a stub
	 */
	boolean stub();

	/**
	 * Returns true if the data fields of this table row have been changed since
	 * the last insert or update into the database.
	 *
	 * @see #dirty()
	 * @return true if this table row has non-persistent modification
	 */
	boolean dataChanged();

	/**
	 * Returns true if a setter on the data fields of this table row has been
	 * called since the last insert or update into the database.
	 * <p>
	 * In contrast to {@link #dataChanged()}, this method will also return
	 * <i>true</i> if the call to the setter had no effect, that is, the new
	 * value was equal to the old value of the field.
	 *
	 * @see #dataChanged()
	 * @return true if a setter was called on this table row
	 */
	boolean dirty();

	/**
	 * Returns true if the given data field of this table row has been changed
	 * since the last insert or update into the database.
	 * <p>
	 * For control fields, this method always returns false.
	 *
	 * @return true if this table row has non-persistent modification of the
	 *         given field
	 */
	boolean dataChanged(IDbField<R, ?> field);

	/**
	 * Returns true if the <i>invalidated</i> flag of this table row is enabled.
	 *
	 * @return true if <i>invalidated</i> flag is enabled
	 */
	boolean invalidated();

	/**
	 * Sets the <i>invalidated</i> flag of this table row to <i>true</i>.
	 * <p>
	 * If this flag is enabled, the next load of this table row from the
	 * database will update its field values. Otherwise, the current field
	 * values will be preserved.
	 */
	void invalidate();

	/**
	 * Returns <i>true</i> if the existence of this table row is not permanent.
	 * <p>
	 * This method returns <i>true</i>
	 * <ul>
	 * <li>for newly created table rows, that have not be saved to the
	 * database.</li>
	 * <li>for tables rows that have been deleted by a call to
	 * {@link #delete()}.</li>
	 * </ul>
	 * <p>
	 * This method returns <i>false</i>
	 * <ul>
	 * <li>for table rows that have been saved to the database.</li>
	 * <li>for table rows that have been loaded from the database.</li>
	 * <li>for table rows that are stubs (stubs are supposed to exist in the
	 * database).</li>
	 * </ul>
	 * <p>
	 * This method does <b>not</b> indicate whether there are non-persistent
	 * changes to this table row or not.
	 *
	 * @return whether this row does not exist in the database
	 */
	boolean impermanent();

	// ------------------------------ save and delete ------------------------------ //

	/**
	 * Does an update or insert for this table row.
	 * <p>
	 * This method simply calls {@link IDbTable#save}.
	 */
	R save();

	/**
	 * Executes an insert or update of this table row if necessary.
	 * <p>
	 * If this table row is an {@link IDbObject}, this method calls
	 * {@link #save()} if {@link #dataChanged()} returns true or if
	 * {@link IDbObject#getId()} returns null.
	 * <p>
	 * If this table row is <b>not</b> an {@link IDbObject} then this method
	 * always calls {@link #save()}.
	 *
	 * @return <i>true</i> if the insert or update was necessary and was
	 *         executed, <i>false</i> otherwise
	 */
	boolean saveIfNecessary();

	/**
	 * Deletes this table row from the database.
	 * <p>
	 * If the primary key of this row is null, this function will throw an
	 * exception.
	 */
	R delete();

	// ------------------------------ loading ------------------------------ //

	/**
	 * Reloads the data of this table row from the database.
	 *
	 * @return <i>true</i> if the table row was reloaded successfully, i.e. it
	 *         still exists in database
	 */
	boolean reload();

	/**
	 * Reloads the data of this table row from the database.
	 * <p>
	 * This method locks the entry in the database with the given locking mode.
	 *
	 * @param lock
	 *            the locking mode to use (if <i>null</i>, no locking is used)
	 * @return <i>true</i> if the table row was reloaded successfully, i.e. it
	 *         still exists in database
	 */
	boolean reload(SqlSelectLock lock);

	// ------------------------------ fields ------------------------------ //

	void copyDataFieldsTo(R target);

	// ------------------------------ basics ------------------------------ //

	/**
	 * Returns a reference to the actual implementing row object.
	 *
	 * @return reference to this object (never null)
	 */
	R getThis();

	/**
	 * Compares the primary key of this row to the primary key of the given row.
	 * <p>
	 * If the table rows have a generated key and at least one of the primary
	 * keys is null, this method returns <i>false</i>.
	 *
	 * @param other
	 *            the other table row
	 * @return <i>true</i> if both table rows have equal and non-null primary
	 *         keys
	 */
	boolean is(R other);

	/**
	 * Compares this table row to another table row.
	 * <p>
	 * Tables rows have identity, so this is actually a comparison of object
	 * identity.
	 *
	 * @param other
	 *            the other table row
	 * @return <i>true</i> if both table rows are the same object
	 */
	@Override
	boolean equals(Object other);

	/**
	 * Returns the hash code for this table row.
	 * <p>
	 * Tables rows have identity, so this returns the identity hash code of this
	 * object.
	 *
	 * @return the identity hash code of this table row
	 */
	@Override
	int hashCode();

	/**
	 * Returns a string representation of this table row.
	 *
	 * @return this object converted into a string
	 */
	@Override
	String toString();
}
