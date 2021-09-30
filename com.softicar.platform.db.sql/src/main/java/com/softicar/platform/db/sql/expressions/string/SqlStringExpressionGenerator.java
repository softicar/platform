package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.expressions.base.AbstractSqlExpressionGenerator;

public class SqlStringExpressionGenerator extends AbstractSqlExpressionGenerator {

	public static void generateAll() {

		for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
			new SqlStringExpressionGenerator(i).generateInterface();
			new SqlStringExpressionGenerator(i).generateClass();
		}
	}

	public SqlStringExpressionGenerator(int tableCount) {

		super(tableCount, String.class);
	}

	@Override
	protected void printInterfaceMethods() {

		printFactoryMethods();
		printCastOperations();
		printUnaryOperations();
		for (int i = 1; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT - getTableCount(); ++i) {
			printSeparator("OPERATIONS " + i);
			printEqualityOperations(i);
			printComparisonOperations(i);

			printBinaryOperation("concat", "CONCAT", i);
			printBinaryOperation(Boolean.class, "isLike", "IS_LIKE", i);
			printBinaryOperation(Boolean.class, "isNotLike", "IS_NOT_LIKE", i);
			printBinaryOperation(Boolean.class, "isRegexp", "IS_REGEXP", i);
			printBinaryOperation(Boolean.class, "isNotRegexp", "IS_NOT_REGEXP", i);
		}
	}

	private void printCastOperations() {

		printSeparator("CASTS");
		printCastOperation(Integer.class);
		printCastOperation(Long.class);
//		printParamertizedCastOperation(Float.class);
//		printParamertizedCastOperation(Double.class);
	}

	private void printUnaryOperations() {

		printSeparator("UNARY OPERATIONS");
		printUnaryOperation(Integer.class, "length", "LENGTH");
	}
}
