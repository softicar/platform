package com.softicar.platform.db.sql;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.date.SqlDayExpression0;
import com.softicar.platform.db.sql.expressions.date.SqlDayTimeExpression0;
import com.softicar.platform.db.sql.expressions.helper.SqlLiteralExpression;
import com.softicar.platform.db.sql.expressions.numeric.SqlBigDecimalExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlDoubleExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlFloatExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression0;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.selects.SqlSelect0_0;
import com.softicar.platform.db.sql.selects.SqlSelect0_1;
import com.softicar.platform.db.sql.selects.SqlSelect1_0;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.math.BigDecimal;

public class SqlGenerator extends AbstractSqlJavaCodePrinter {

	public static final int MAX_EXPRESSION_TABLE_COUNT = 4;
	private static final String CLASS_NAME = "Sql";

	public static void generateAll() {

		SqlGenerator generator = new SqlGenerator();
		generator.generate();
		generator.writeOutToSourceFolder(CLASS_NAME);
	}

	private void generate() {

		addImport(ISqlExpression0.class);
		addImport(SqlValueTypes.class);

		beginBlock("public class %s", CLASS_NAME);
		printSelectMethods();
		printFromMethod();
		printLiteralMethods();
		printCommonMethods();
		endBlock();
	}

	private void printSelectMethods() {

		printSeparator("select");

		addImport(SqlSelect0_0.class);
		beginMethod("public static %s select()", SqlSelect0_0.class.getSimpleName());
		println("return new %s();", SqlSelect0_0.class.getSimpleName());
		endMethod();

		addImport(SqlSelect0_1.class);
		beginMethod("public static <V0> %s<V0> select(%s<V0> expression)", SqlSelect0_1.class.getSimpleName(), ISqlExpression0.class.getSimpleName());
		println("return select().select(expression);");
		endMethod();
	}

	private void printFromMethod() {

		printSeparator("from");

		addImport(ISqlTable.class);
		addImport(SqlSelect1_0.class);
		beginMethod("public static <T0> %s<T0> from(%s<T0> table)", SqlSelect1_0.class.getSimpleName(), ISqlTable.class.getSimpleName());
		println("return select().from(table);");
		endMethod();
	}

	private void printLiteralMethods() {

		printSeparator("literal");

		addImport(Day.class);
		addImport(DayTime.class);
		addImport(BigDecimal.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".BOOLEAN", Boolean.class, SqlBooleanExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".STRING", String.class, SqlStringExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".INTEGER", Integer.class, SqlIntegerExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".LONG", Long.class, SqlLongExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".FLOAT", Float.class, SqlFloatExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".DOUBLE", Double.class, SqlDoubleExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".BIG_DECIMAL", BigDecimal.class, SqlBigDecimalExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".DAY", Day.class, SqlDayExpression0.class);
		generateLiteralFunction(SqlValueTypes.class.getSimpleName() + ".DAY_TIME", DayTime.class, SqlDayTimeExpression0.class);
	}

	private void generateLiteralFunction(String valueType, Class<?> type, Class<?> expressionClass) {

		addImport(expressionClass);
		addImport(SqlLiteralExpression.class);
		beginMethod("public static %s literal(%s value)", expressionClass.getSimpleName(), type.getSimpleName());
		println("return new %s(%s.create(%s, value));", expressionClass.getSimpleName(), SqlLiteralExpression.class.getSimpleName(), valueType);
		endMethod();
	}

	private void printCommonMethods() {

		printSeparator("common");
		addImport(SqlOperations.class);

		addImport(DayTime.class);
		addImport(SqlDayTimeExpression0.class);
		beginMethod("public static ISqlExpression0<DayTime> now()");
		println("return new %s(SqlOperations.NOW.create(%s.DAY_TIME));", SqlDayTimeExpression0.class.getSimpleName(), SqlValueTypes.class.getSimpleName());
		endMethod();

		addImport(SqlIntegerExpression0.class);
		beginMethod("public static ISqlExpression0<Integer> count()");
		println("return new %s(SqlOperations.COUNT_ALL.create(%s.INTEGER));", SqlIntegerExpression0.class.getSimpleName(), SqlValueTypes.class.getSimpleName());
		endMethod();
	}
}
