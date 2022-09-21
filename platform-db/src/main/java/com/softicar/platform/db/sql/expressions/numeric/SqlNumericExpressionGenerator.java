package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.expressions.base.AbstractSqlExpressionGenerator;
import java.math.BigDecimal;

/**
 * Generator for all numeric SQL expression classes.
 *
 * @author Oliver Richers
 */
public class SqlNumericExpressionGenerator extends AbstractSqlExpressionGenerator {

	public static void generateAll() {

		for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
			new SqlNumericExpressionGenerator(i, Integer.class).generateInterface();
			new SqlNumericExpressionGenerator(i, Long.class).generateInterface();
			new SqlNumericExpressionGenerator(i, Float.class).generateInterface();
			new SqlNumericExpressionGenerator(i, Double.class).generateInterface();
			new SqlNumericExpressionGenerator(i, BigDecimal.class).generateInterface();

			new SqlNumericExpressionGenerator(i, Integer.class).generateClass();
			new SqlNumericExpressionGenerator(i, Long.class).generateClass();
			new SqlNumericExpressionGenerator(i, Float.class).generateClass();
			new SqlNumericExpressionGenerator(i, Double.class).generateClass();
			new SqlNumericExpressionGenerator(i, BigDecimal.class).generateClass();
		}
	}

	public SqlNumericExpressionGenerator(int tableCount, Class<?> valueType) {

		super(tableCount, valueType);
		addImport(valueType);
	}

	@Override
	protected void printInterfaceMethods() {

		printFactoryMethods();
		printCastOperations();
		for (int i = 1; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT - getTableCount(); ++i) {
			printSeparator("OPERATIONS " + i);
			printEqualityOperations(i);
			printComparisonOperations(i);

			printBinaryOperation("plus", "PLUS", i);
			printBinaryOperation("minus", "MINUS", i);
			printBinaryOperation("times", "TIMES", i);
			printBinaryOperation("modulo", "MODULO", i);
			printDividedOperation(i);
		}
	}

	private void printDividedOperation(int i) {

		if (isDecimal()) {
			printBinaryOperation("divided", "DECIMAL_DIVIDED", i);
		} else {
			printBinaryOperation("divided", "INTEGRAL_DIVIDED", i);
		}
	}

	private void printCastOperations() {

		printSeparator("CASTS");
		printCastOperation(String.class);
		printCastOperation(Float.class);
		printCastOperation(Double.class);
		printCastOperation(BigDecimal.class);
		if (isIntegral()) {
			// decimal types should be converted via round, ceil or floor
			printCastOperation(Integer.class);
			printCastOperation(Long.class);
		}
	}
}
