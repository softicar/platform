package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.statement.ISqlConditionalStatement;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.Collection;
import java.util.Comparator;

/**
 * Represents the primary key of a database table;
 *
 * @param <R>
 *            the type of the table rows
 * @param <P>
 *            the type of the primary key values
 * @author Oliver Richers
 */
public interface IDbPrimaryKey<R, P> extends IDbKey<R>, Comparator<P> {

	@Override
	default DbKeyType getType() {

		return DbKeyType.PRIMARY;
	}

	@Override
	default boolean isPrimaryKey() {

		return true;
	}

	@Override
	default boolean isUniqueKey() {

		return true;
	}

	@Override
	default String getName() {

		return null;
	}

	/**
	 * Returns <i>true</i> if this key contains the given field.
	 *
	 * @param field
	 *            the field to test
	 * @return <i>true</i> if this key contains the field
	 */
	boolean containsField(IDbField<R, ?> field);

	/**
	 * Adds a condition to the given SQL statement for the specified key value.
	 *
	 * @param statement
	 *            the statement to extend
	 * @param keyValue
	 *            the key value
	 */
	void addKeyCondition(ISqlConditionalStatement<R, ?> statement, P keyValue);

	/**
	 * Creates an {@link ISqlBooleanExpression} comparing this key to the given
	 * value.
	 *
	 * @param keyValue
	 *            the key value (may be null)
	 * @return the boolean expressions (never null)
	 */
	ISqlBooleanExpression<R> isEqual(P keyValue);

	/**
	 * Creates an {@link ISqlBooleanExpression} testing if this key matches one
	 * of the given key values.
	 * <p>
	 * If no key values are supplied, this returns the SQL expression
	 * <i>FALSE</i>.
	 *
	 * @param keyValues
	 *            the key values (may be null)
	 * @return the boolean expressions (never null)
	 */
	ISqlBooleanExpression<R> isIn(Collection<P> keyValues);

	/**
	 * Reads the field value from the given key value.
	 *
	 * @param field
	 *            the field to read the value for
	 * @param keyValue
	 *            the key value
	 * @return the field value
	 */
	<V> V getValue(IDbField<R, V> field, P keyValue);

	/**
	 * Reads the key value from the given table row.
	 *
	 * @param row
	 *            the table row to read from
	 * @return the key value
	 */
	P getFromRow(R row);

	/**
	 * Reads the key value from the given {@link DbResultSet}.
	 *
	 * @param resultSet
	 *            the result set to read from
	 * @param baseIndex
	 *            the relative base index (usually 1)
	 * @return the key value
	 */
	P getFromResultSet(DbResultSet resultSet, int baseIndex);

	/**
	 * Returns <i>true</i> if this is a generated key.
	 *
	 * @return <i>true</i> if the key values are generated
	 */
	boolean isGenerated();

	/**
	 * Returns <i>true</i> if the primary key is a base table row.
	 *
	 * @return <i>true</i> if the key values are base table rows
	 */
	boolean isBase();

	/**
	 * Returns the ID field of this key.
	 * <p>
	 * At the moment, you may only call this method if this is a generated key
	 * of type {@link Integer}.
	 *
	 * @return the ID field
	 * @throws UnsupportedOperationException
	 *             if this key does not provide an ID field
	 */
	IDbField<R, Integer> getIdField();
}
