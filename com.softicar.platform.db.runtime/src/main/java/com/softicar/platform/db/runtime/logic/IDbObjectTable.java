package com.softicar.platform.db.runtime.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbTable;

/**
 * Represents an {@link IDbTable} with an identity column.
 * <p>
 * Table rows of an object table have an identity in form of a generated
 * surrogate key of type {@link Integer}. New <i>table row objects</i> are first
 * created with an ID of <i>null</i>. Only when they are saved to the database
 * table a new ID is generated and assigned to the row object.
 * <p>
 * There a different ways to get an instance of a table row object:
 * <ul>
 * <li>to create new rows, call {@link #createObject}</li>
 * <li>to load an existing row by ID, call {@link #get}</li>
 * <li>to get an existing row without database access, call
 * {@link #getStub}</li>
 * </ul>
 * <p>
 * There are also method to get multiple table row objects at once:
 * <ul>
 * <li>to load existing rows, call {@link #getAll}</li>
 * <li>to load existing rows by a SELECT, call {@link #createSelect}</li>
 * </ul>
 *
 * @author Oliver Richers
 */
public interface IDbObjectTable<R extends IDbObject<R>> extends IDbEntityTable<R, Integer> {

	/**
	 * Creates a new initialized instance of {@link IDbObject}.
	 * <p>
	 * The ID of the new row object will be <i>null</i>.
	 *
	 * @return new database object (never null)
	 */
	@Override
	default R createObject() {

		return getRowFactory().get();
	}

	/**
	 * Returns the primary key field of this {@link IDbObjectTable}.
	 * <p>
	 * The ID field is of type {@link Integer} and holds the generated surrogate
	 * keys.
	 *
	 * @return the primary key field (never null)
	 */
	default IDbField<R, Integer> getIdField() {

		return getPrimaryKey().getIdField();
	}

	/**
	 * Sets the auto-increment ID counter of this given table to the given
	 * number.
	 * <p>
	 * The next record to be inserted will receive the given number as an ID.
	 * <p>
	 * Warning: Currently only supported for the H2 in-memory database.
	 *
	 * @param newAutoIdCounter
	 *            the new ID counter
	 */
	default void setAutoIdCounter(int newAutoIdCounter) {

		if (DbConnections.isServerType(DbServerType.H2_MEMORY)) {
			String statement = String.format("ALTER TABLE %s ALTER COLUMN %s RESTART WITH %s", getFullName(), getIdField(), newAutoIdCounter);
			new DbStatement()//
				.addTable(this)
				.addText(statement)
				.execute();
		} else {
			throw new UnsupportedOperationException("Setting of auto-increment counter only supported for H2 in-memory databases.");
		}
	}
}
