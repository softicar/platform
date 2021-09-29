package com.softicar.platform.db.sql.expressions.builder.bases;

import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlNullaryOperationBuilder;
import com.softicar.platform.db.sql.operations.SqlNullaryOperation;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Implements the factory methods of the operation builder.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractSqlNullaryOperationBuilder implements ISqlNullaryOperationBuilder {

	@Override
	public final <VALUE> SqlNullaryOperation<VALUE> create(ISqlValueType<VALUE> valueType) {

		return new SqlNullaryOperation<>(this, valueType);
	}
}
