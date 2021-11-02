package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression1;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlBinaryOperationBuilder;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public class SqlForeignFields {

	/**
	 * Returns the primary key of the given foreign table row.
	 *
	 * @param field
	 *            the table field referencing the foreign table row
	 * @param foreignRow
	 *            the foreign table row
	 * @return the primary key value of the foreign table row
	 * @throws IllegalArgumentException
	 *             if the primary key of the foreign table row is <i>null</i>
	 */
	public static <R, F, FP> FP getPkOrThrow(ISqlForeignRowField<R, F, FP> field, F foreignRow) {

		FP pk = field.getTargetField().getValue(foreignRow);
		if (pk != null) {
			return pk;
		} else {
			throw new IllegalArgumentException("Primary key of table row may not be null.");
		}
	}

	public static <R, F, FP> SqlBooleanExpression1<R> createBooleanExpression(//
			ISqlBinaryOperationBuilder operationBuilder, ISqlForeignRowField<R, F, FP> field, FP value) {

		return new SqlBooleanExpression1<>(operationBuilder.create(SqlValueTypes.BOOLEAN, field, field.getTargetField().literal(value)));
	}
}
