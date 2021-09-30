package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Builds a cast expression in the form CAST(expression AS type).
 *
 * @author Oliver Richers
 */
public class SqlCastBuilder extends AbstractSqlUnaryOperationBuilder {

	private final Supplier<String> typeNameSupplier;

	public SqlCastBuilder(String typeName) {

		this.typeNameSupplier = () -> typeName;
	}

	public SqlCastBuilder(Function<IDbServerQuirks, String> quirksGetter) {

		this.typeNameSupplier = () -> quirksGetter.apply(DbConnections.getServerQuirks());
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression) {

		builder.addText("CAST(");
		expression.build(builder);
		builder.addText(" AS ").addText(typeNameSupplier.get()).addText(")");
	}
}
