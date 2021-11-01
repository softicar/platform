package com.softicar.platform.db.sql.field;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;

/**
 * Represents a field of an {@link ISqlTable}.
 *
 * @param <R>
 *            the table row type of the table containing this field
 * @param <V>
 *            the value type of this field
 * @author Oliver Richers
 */
public interface ISqlField<R, V> extends ISqlExpression1<V, R> {

	// ---------------------------------------- basics ---------------------------------------- //

	/**
	 * Returns the name of this field.
	 *
	 * @return {@link #getName()}
	 */
	@Override
	String toString();

	// ---------------------------------------- buildable methods ---------------------------------------- //

	@Override
	void build(ISqlClauseBuilder builder);

	// ---------------------------------------- expression methods ---------------------------------------- //

	@Override
	ISqlValueType<V> getValueType();

	@Override
	int getColumnCount();

	// ---------------------------------------- field methods ---------------------------------------- //

	/**
	 * Returns the table this field belongs to.
	 *
	 * @return the table of this field
	 */
	ISqlTable<R> getTable();

	/**
	 * Returns the index of the field within the table, starting from zero.
	 *
	 * @return the index of the field
	 */
	int getIndex();

	/**
	 * Returns the type of this field.
	 *
	 * @return field type
	 * @see SqlFieldType
	 */
	SqlFieldType getFieldType();

	/**
	 * Returns the physical {@link SqlFieldType} of this field.
	 * <p>
	 * This method is a workaround which is necessary as long as we need to
	 * support fields which have a physical type that differs from their logical
	 * type. One such exceptional case currently exists:
	 * <ul>
	 * <li>ID fields of physical type {@link SqlFieldType#LONG}, which are
	 * currently always mapped to logical type {@link SqlFieldType#INTEGER}</li>
	 * </ul>
	 *
	 * @return the physical {@link SqlFieldType} (never null)
	 */
	default SqlFieldType getPhysicalFieldType() {

		return getFieldType();
	}

	/**
	 * Returns the unquoted name of this field.
	 *
	 * @return the name of this field
	 */
	String getName();

	/**
	 * Returns the value of this field for the given table row.
	 *
	 * @param row
	 *            the row (never null)
	 * @return the value (may be null)
	 */
	V getValue(R row);

	/**
	 * Sets the value of this field for the given table row.
	 *
	 * @param row
	 *            the row (never null)
	 * @param value
	 *            the value to set (may be null)
	 * @return <i>true</i> if a new value was assigned, <i>false</i> if the new
	 *         value is equal to the current value
	 */
	boolean setValue(R row, V value);
}
