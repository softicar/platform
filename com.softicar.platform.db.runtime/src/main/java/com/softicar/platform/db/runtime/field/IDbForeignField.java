package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.utils.IDbObjectList;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression1;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression1;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

/**
 * Represents an {@link IDbField} that references an {@link IDbObjectTable}.
 * <p>
 * The values of this field are instances of {@link IDbObject} of the referenced
 * {@link IDbObjectTable}.
 *
 * @author Oliver Richers
 */
public interface IDbForeignField<R, F extends IDbObject<F>> extends IDbForeignRowField<R, F, Integer> {

	/**
	 * Returns the ID of the foreign object referenced by the given object.
	 * <p>
	 * This method uses {@link #getValue} to get the foreign value of this
	 * field. If the foreign value is not <i>null</i>, this method returns its
	 * ID, otherwise <i>null</i> will be returned.
	 *
	 * @param object
	 *            the object to read from
	 * @return the ID of the foreign object or null
	 */
	Integer getValueId(R object);

	@Override
	IDbObjectList<F> prefetch(Iterable<? extends R> objects);

	// -------------------- isEqualId() and isNotEqualId() -------------------- //

	/**
	 * Equivalent to {@link #isEqualPk}.
	 */
	default ISqlBooleanExpression<R> isEqualId(Integer foreignId) {

		return isEqualPk(foreignId);
	}

	/**
	 * Equivalent to {@link #isNotEqualPk}.
	 */
	default ISqlBooleanExpression<R> isNotEqualId(Integer foreignId) {

		return isNotEqualPk(foreignId);
	}

	// -------------------- isInIds() and isNotInIds() -------------------- //

	/**
	 * Equivalent to {@link #isInPks}.
	 */
	default ISqlBooleanExpression<R> isInIds(Integer...ids) {

		return isInPks(ids);
	}

	/**
	 * Equivalent to {@link #isInPks}.
	 */
	default ISqlBooleanExpression<R> isInIds(Iterable<Integer> ids) {

		return isInPks(ids);
	}

	/**
	 * Equivalent to {@link #isNotInPks}.
	 */
	default ISqlBooleanExpression<R> isNotInIds(Integer...ids) {

		return isNotInPks(ids);
	}

	/**
	 * Equivalent to {@link #isNotInPks}.
	 */
	default ISqlBooleanExpression<R> isNotInIds(Iterable<Integer> ids) {

		return isNotInPks(ids);
	}

	// -------------------- castToInteger() -------------------- //

	default ISqlIntegerExpression1<R> castToInteger() {

		return new SqlIntegerExpression1<>(SqlOperations.NOP.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------- methods for backwards-compatibility -------------------- //

	/**
	 * Equivalent to {@link #isEqualPk}.
	 */
	default ISqlBooleanExpression<R> equalID(Integer foreignId) {

		return isEqualPk(foreignId);
	}

	/**
	 * Equivalent to {@link #isNotEqualPk}.
	 */
	default ISqlBooleanExpression<R> notEqualID(Integer foreignId) {

		return isNotEqualPk(foreignId);
	}

	/**
	 * Equivalent to {@link #isInPks}.
	 */
	default ISqlBooleanExpression<R> inIDList(Iterable<Integer> ids) {

		return isInPks(ids);
	}
}
