package com.softicar.platform.db.sql.expressions.builder.interfaces;

import com.softicar.platform.db.sql.operations.SqlNullaryOperation;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Expression builder for a nullary operation.
 * 
 * @author Oliver Richers
 */
public interface ISqlNullaryOperationBuilder {

	void build(ISqlClauseBuilder builder);

	<VALUE> SqlNullaryOperation<VALUE> create(ISqlValueType<VALUE> valueType);
}
