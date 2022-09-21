package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.expressions.base.AbstractSqlExpressionGenerator;
import com.softicar.platform.db.sql.operations.SqlOperations;

public class SqlBooleanExpressionGenerator extends AbstractSqlExpressionGenerator {

	public static void generateAll() {

		for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
			new SqlBooleanExpressionGenerator(i).generateInterface();
			new SqlBooleanExpressionGenerator(i).generateClass();
		}
	}

	public SqlBooleanExpressionGenerator(int tableCount) {

		super(tableCount, Boolean.class);
	}

	@Override
	protected String getTableCountDependentSuperInterfaceType() {

		if (getTableCount() == 1) {
			addImport(ISqlBooleanExpression.class);
			return ISqlBooleanExpression.class.getSimpleName() + "<T0>";
		} else {
			return super.getTableCountDependentSuperInterfaceType();
		}
	}

	@Override
	protected void printInterfaceMethods() {

		printFactoryMethods();
		if (getTableCount() == 1) {
			addImport(SqlOperations.class);
			println("@Override");
			beginBlock("default %s<T0> and(%s<T0> other)", ISqlBooleanExpression1.class.getSimpleName(), ISqlBooleanExpression.class.getSimpleName());
			println(
				"return new %s<>(%s.AND.create(this, other).setFixTable(true));",
				SqlBooleanExpression1.class.getSimpleName(),
				SqlOperations.class.getSimpleName());
			endBlock();
			println("@Override");
			beginBlock("default %s<T0> or(%s<T0> other)", ISqlBooleanExpression1.class.getSimpleName(), ISqlBooleanExpression.class.getSimpleName());
			println(
				"return new %s<>(%s.OR.create(this, other).setFixTable(true));",
				SqlBooleanExpression1.class.getSimpleName(),
				SqlOperations.class.getSimpleName());
			endBlock();
		}
		for (int i = 1; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT - getTableCount(); ++i) {
			printSeparator("OPERATIONS " + i);
			printEqualityOperations(i);
			printBinaryOperation("and", "AND", i);
			printBinaryOperation("or", "OR", i);
		}
	}
}
