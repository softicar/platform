package com.softicar.platform.db.sql.field;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;
import com.softicar.platform.db.sql.fields.SqlForeignFields;
import com.softicar.platform.db.sql.operations.SqlInPkListExpression;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.Arrays;

/**
 * Represents an {@link ISqlField} that points to the primary key of another
 * {@link ISqlTable}.
 *
 * @param <R>
 *            the table row type of the table containing this field
 * @param <F>
 *            the table row type of the target table
 * @param <FP>
 *            the type of the primary key of the target table
 * @author Oliver Richers
 */
public interface ISqlForeignRowField<R, F, FP> extends ISqlField<R, F> {

	/**
	 * The primary key field of the target {@link ISqlTable}.
	 *
	 * @return the target {@link ISqlValueField} (never null)
	 */
	ISqlField<F, FP> getTargetField();

	/**
	 * Returns the target {@link ISqlTable} that this field points to.
	 *
	 * @return the target {@link ISqlTable} (never null)
	 */
	ISqlTable<F> getTargetTable();

	// -------------------- isEqual(F) and isNotEqual(F) -------------------- //

	@Override
	default ISqlBooleanExpression<R> isEqual(F foreign) {

		// this check is important
		return foreign != null? isEqualPk(SqlForeignFields.getPkOrThrow(this, foreign)) : isNull();
	}

	@Override
	default ISqlBooleanExpression<R> isNotEqual(F foreign) {

		// this check is important
		return foreign != null? isNotEqualPk(SqlForeignFields.getPkOrThrow(this, foreign)) : isNotNull();
	}

	// -------------------- isEqual(field) and isNotEqual(field) -------------------- //

	default <S> ISqlBooleanExpression2<R, S> isEqual(ISqlForeignRowField<S, F, FP> other) {

		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S> ISqlBooleanExpression2<R, S> isNotEqual(ISqlForeignRowField<S, F, FP> other) {

		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------- isEqualToTargetField() and isNotEqualToTargetField() -------------------- //

	default SqlBooleanExpression2<R, F> isEqualToTargetField() {

		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, getTargetField()));
	}

	default SqlBooleanExpression2<R, F> isNotEqualToTargetField() {

		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, getTargetField()));
	}

	// -------------------- isEqualPk() and isNotEqualPk() -------------------- //

	/**
	 * Checks if the PK of this foreign field is equivalent to the given PK.
	 * <p>
	 * If the given PK is <i>null</i>, this method will be equivalent to calling
	 * {@link #isNull()}.
	 *
	 * @param foreignPk
	 *            the PK of the foreign table row or null
	 * @return boolean expression representing the comparison
	 */
	default ISqlBooleanExpression<R> isEqualPk(FP foreignPk) {

		return foreignPk != null? SqlForeignFields.createBooleanExpression(SqlOperations.IS_EQUAL, this, foreignPk) : isNull();
	}

	/**
	 * Checks if the PK of this foreign field not equivalent to the given PK.
	 * <p>
	 * If the given PK is <i>null</i>, this method will be equivalent to calling
	 * {@link #isNotNull()}.
	 *
	 * @param foreignPk
	 *            the PK of the foreign table row or null
	 * @return boolean expression representing the comparison
	 */
	default ISqlBooleanExpression<R> isNotEqualPk(FP foreignPk) {

		return foreignPk != null? SqlForeignFields.createBooleanExpression(SqlOperations.IS_NOT_EQUAL, this, foreignPk) : isNotNull();
	}

	// -------------------- isInPks() and isNotInPks() -------------------- //

	@SuppressWarnings("unchecked")
	default ISqlBooleanExpression<R> isInPks(FP...pks) {

		return isInPks(Arrays.asList(pks));
	}

	default ISqlBooleanExpression<R> isInPks(Iterable<FP> pks) {

		return wrapBool(SqlInPkListExpression.Type.IS_IN.get(this, pks));
	}

	@SuppressWarnings("unchecked")
	default ISqlBooleanExpression<R> isNotInPks(FP...pks) {

		return isNotInPks(Arrays.asList(pks));
	}

	default ISqlBooleanExpression<R> isNotInPks(Iterable<FP> pks) {

		return wrapBool(SqlInPkListExpression.Type.IS_NOT_IN.get(this, pks));
	}
}
