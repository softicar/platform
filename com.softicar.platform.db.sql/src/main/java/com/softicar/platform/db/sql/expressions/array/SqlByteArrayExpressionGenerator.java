package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.expressions.base.AbstractSqlExpressionGenerator;

public class SqlByteArrayExpressionGenerator extends AbstractSqlExpressionGenerator {

	public static void generateAll() {

		for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
			new SqlByteArrayExpressionGenerator(i).generateInterface();
			new SqlByteArrayExpressionGenerator(i).generateClass();
		}
	}

	public SqlByteArrayExpressionGenerator(int tableCount) {

		super(tableCount, byte[].class);
	}

	@Override
	protected void printInterfaceMethods() {

		printFactoryMethods();
		printUnaryOperations();
		for (int i = 1; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT - getTableCount(); ++i) {
			printSeparator("OPERATIONS " + i);
			printEqualityOperations(i);
		}
	}

	private void printUnaryOperations() {

		printSeparator("UNARY OPERATIONS");
		printUnaryOperation(Integer.class, "length", "LENGTH");
	}
}
