package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlTrinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

public class SqlParameterizedCastBuilder extends AbstractSqlTrinaryOperationBuilder {

	private final String typeName;

	public SqlParameterizedCastBuilder(String typeName) {

		this.typeName = typeName;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2, ISqlExpression<?> expression3) {

		builder.addText("CAST(");
		expression1.build(builder);
		builder.addText(" AS ").addText(typeName).addText("(");
		expression2.build(builder);
		builder.addText(", ");
		expression3.build(builder);
		builder.addText(")");
	}
}
