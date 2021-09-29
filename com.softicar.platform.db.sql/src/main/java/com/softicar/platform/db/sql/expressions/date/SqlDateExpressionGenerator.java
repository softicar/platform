package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.expressions.base.AbstractSqlExpressionGenerator;

/**
 * Generator for all SQL expression classes of type {@link Day} and
 * {@link DayTime}.
 *
 * @author Oliver Richers
 */
public class SqlDateExpressionGenerator extends AbstractSqlExpressionGenerator {

	public static void generateAll() {

		for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
			new SqlDateExpressionGenerator(i, Day.class).generateInterface();
			new SqlDateExpressionGenerator(i, Day.class).generateClass();
			new SqlDateExpressionGenerator(i, DayTime.class).generateInterface();
			new SqlDateExpressionGenerator(i, DayTime.class).generateClass();
			new SqlDateExpressionGenerator(i, Time.class).generateInterface();
			new SqlDateExpressionGenerator(i, Time.class).generateClass();
		}
	}

	public SqlDateExpressionGenerator(int tableCount, Class<?> valueType) {

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
		}
	}

	private void printCastOperations() {

		printSeparator("CASTS");
		printCastOperation(Day.class);
		printCastOperation(DayTime.class);
		printCastOperation(String.class);
	}
}
