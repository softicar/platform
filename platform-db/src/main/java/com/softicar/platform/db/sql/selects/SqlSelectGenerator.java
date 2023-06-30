package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.db.sql.AbstractSqlJavaCodePrinter;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.SqlMasterGenerator;
import com.softicar.platform.db.sql.choosers.SqlTableChooserGenerator;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.generation.SqlCodeGenerationUtils;
import com.softicar.platform.db.sql.selects.SqlSelectBase.SelectChooserBase;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public class SqlSelectGenerator extends AbstractSqlJavaCodePrinter {

	private final int tableCount;
	private final int valueCount;
	private final String className;

	public static void generateAll() {

		for (int i = 0; i <= SqlMasterGenerator.MAX_TABLE_COUNT; ++i) {
			for (int j = 0; j <= SqlMasterGenerator.MAX_VALUE_COUNT; ++j) {
				if (i == 0 && j == 0) {
					continue;
				}

				SqlSelectGenerator generator = new SqlSelectGenerator(i, j);
				generator.generate();
				generator.writeOutToSourceFolder(getRawClassName(i, j));
			}
		}
	}

	private SqlSelectGenerator(int tableCount, int valueCount) {

		this.tableCount = tableCount;
		this.valueCount = valueCount;
		this.className = getClassName(tableCount, valueCount);
	}

	private void generate() {

		String declaredClassName = getDeclaredClassName(tableCount, valueCount);
		beginClass("public final class %s%s%s", declaredClassName, getExtends(), getImplements());

		// print constructor
		if (valueCount == 0) {
			beginMethod("protected %s()", getRawClassName(tableCount, valueCount));
			println("// protected");
			endMethod();
		} else {
			addImport(ISqlExpression.class);
			String otherBaseType = String.format("SqlSelectBase%d%s", valueCount - 1, SqlCodeGenerationUtils.implode("V", valueCount - 1));
			beginMethod(
				"protected %s(%s other, %s<V%d> expression)",
				getRawClassName(tableCount, valueCount),
				otherBaseType,
				ISqlExpression.class.getSimpleName(),
				valueCount - 1);
			println("super(other, expression);");
			endMethod();
		}

		// print getThis() method
		println("@Override");
		beginMethod("public %s getThis()", className);
		println("return this;");
		endMethod();

		// print select() methods
		if (valueCount < SqlMasterGenerator.MAX_VALUE_COUNT) {
			printSeparator("select");

			// select() for chooser
			beginMethod("public SelectChooser0 select()");
			println("return new SelectChooser0();");
			endMethod();

			// select() for literal expression
			beginMethod(//
				"public <V> %s%s select(%s<V> expression)",
				getRawClassName(tableCount, valueCount + 1),
				SqlCodeGenerationUtils.implode("T", tableCount, "V", valueCount, "V"),
				ISqlExpression0.class.getSimpleName());
			println("return select().x(expression);");
			endMethod();

			// select() for expression on last table
			if (tableCount > 0) {
				beginMethod(//
					"public <V> %s%s select(%s<V, T%d> expression)",
					getRawClassName(tableCount, valueCount + 1),
					SqlCodeGenerationUtils.implode("T", tableCount, "V", valueCount, "V"),
					ISqlExpression1.class.getSimpleName(),
					tableCount - 1);
				println("return select().t%d(expression);", tableCount - 1);
				endMethod();
			}
		}

		// print join() methods
		if (tableCount > 0 && tableCount < SqlMasterGenerator.MAX_TABLE_COUNT) {
			printSeparator("join");

			addImport(ISqlTable.class);
			addImport(JoinType.class);
			String resultType = String.format("%s", getClassName(tableCount + 1, valueCount));
			printJoinFunctions(resultType, "JOIN", "join");
			printJoinFunctions(resultType, "LEFT_JOIN", "joinLeft");
		}

		// print on() methods
		if (tableCount > 1) {
			printSeparator("on");

			// on() for chooser
			addImport(PartType.class);
			beginMethod("public %s on()", getTableChooserType("Boolean"));
			println("return new %s<>(this, PartType.JOIN);", getTableChooserSimpleName());
			endMethod();

			// on() for last two tables
			addImport(ISqlBooleanExpression2.class);
			int a = tableCount - 2;
			int b = tableCount - 1;
			beginMethod(
				"public %s on(%s<T%s, T%s> condition)",
				getDeclaredClassName(tableCount, valueCount),
				ISqlBooleanExpression2.class.getSimpleName(),
				a,
				b);
			println("return on().t%s().t%s(condition);", a, b);
			endMethod();
		}

		// print where() methods
		if (tableCount > 0) {
			printSeparator("where");

			// where() for chooser
			addImport(PartType.class);
			beginMethod("public %s where()", getTableChooserType("Boolean"));
			println("return new %s<>(this, PartType.WHERE);", getTableChooserSimpleName());
			endMethod();
		}

		// print groupBy() methods
		if (tableCount > 0) {
			printSeparator("group by");

			// groupBy() for chooser
			addImport(PartType.class);
			beginMethod("public %s groupBy()", getTableChooserType("Object"));
			println("return new %s<>(this, PartType.GROUP_BY);", getTableChooserSimpleName());
			endMethod();
		}

		// print having() methods
		if (tableCount > 0) {
			printSeparator("having");

			// having() for chooser
			addImport(PartType.class);
			beginMethod("public %s having()", getTableChooserType("Boolean"));
			println("return new %s<>(this, PartType.HAVING);", getTableChooserSimpleName());
			endMethod();
		}

		// print orderBy() methods
		if (tableCount > 0) {
			printSeparator("order by");

			// orderBy() for chooser and explicit order direction
			addImport(PartType.class);
			beginMethod("public %s orderBy(OrderDirection direction)", getTableChooserType("Object"));
			println("getCore().setOrderDirection(direction);");
			println("return new %s<>(this, PartType.ORDER_BY);", getTableChooserSimpleName());
			endMethod();

			// orderBy() for chooser
			addImport(OrderDirection.class);
			beginMethod("public %s orderBy()", getTableChooserType("Object"));
			println("return orderBy(OrderDirection.ASCENDING);");
			endMethod();

			// orderByDesc() for chooser
			beginMethod("public %s orderByDesc()", getTableChooserType("Object"));
			println("return orderBy(OrderDirection.DESCENDING);");
			endMethod();
		}

		// print SelectChooser classes
		printSeparator("select chooser");
		if (valueCount < SqlMasterGenerator.MAX_VALUE_COUNT) {
			if (tableCount > 0) {
				for (int i = 0; i <= SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++i) {
					printSelectChooser(i);
				}
			} else {
				printSelectChooser(0);
			}
		}

		endClass();
	}

	private String getTableChooserType(String expressionType) {

		return String.format("%s%s", getTableChooserSimpleName(), SqlCodeGenerationUtils.implode(expressionType, className, "T", tableCount));
	}

	private String getTableChooserSimpleName() {

		String name = SqlCodeGenerationUtils.getTableChooserName(tableCount, 0).getRawName();
		addImport(new JavaPackageName(SqlTableChooserGenerator.class).getClassName(name));
		return name;
	}

	private String getExtends() {

		return String.format(" extends SqlSelectBase%d%s", valueCount, SqlCodeGenerationUtils.implode("V", valueCount));
	}

	private String getImplements() {

		if (tableCount > 0) {
			addImport(ISqlSelectLastTableExtension.class);
			return String
				.format(//
					" implements %s<%s, T%d>",
					ISqlSelectLastTableExtension.class.getSimpleName(),
					className,
					tableCount - 1);
		} else {
			addImport(ISqlSelectExtension.class);
			return String
				.format(//
					" implements %s<%s>",
					ISqlSelectExtension.class.getSimpleName(),
					className);
		}
	}

	private void printSelectChooser(int n) {

		beginClass("public final class SelectChooser%d%s extends %s", n, SqlCodeGenerationUtils.implode("E", n), SelectChooserBase.class.getSimpleName());

		String nextSelectTypeRaw = String.format("SqlSelect%d_%d", tableCount, valueCount + 1);
		String nextSelectType = nextSelectTypeRaw + SqlCodeGenerationUtils.implode("T", tableCount, "V", valueCount, "V");

		// T0(), T1(), ... functions
		if (n < SqlGenerator.MAX_EXPRESSION_TABLE_COUNT) {
			for (int i = 0; i < tableCount; ++i) {
				String nextExpressionType = String.format("ISqlExpression%d%s", n + 1, SqlCodeGenerationUtils.implode("V", "E", n, "T" + i));
				println("public <V> %s t%d(%s expression) { return t%d().x(expression); }", nextSelectType, i, nextExpressionType, i);
			}
		}

		// T0(expression), T1(expression), ... functions
		if (n < SqlGenerator.MAX_EXPRESSION_TABLE_COUNT) {
			for (int i = 0; i < tableCount; ++i) {
				String nextChooserType = String.format("SelectChooser%d%s", n + 1, SqlCodeGenerationUtils.implode("E", n, "T" + i));
				String nextChooserTypeDiamond = String.format("SelectChooser%d", n + 1);
				println("public %s t%d() { return new %s<>(this, %d); }", nextChooserType, i, nextChooserTypeDiamond, i);
			}
		}

		// x(expression) function
		addImport(new JavaPackageName(ISqlExpression.class).getClassName("ISqlExpression" + n));
		String expressionType = String.format("ISqlExpression%d%s", n, SqlCodeGenerationUtils.implode("V", "E", n));
		println(
			"<V> %s x(%s expression) { return addExpression(new %s(%s.this, expression), expression); }",
			nextSelectType,
			expressionType,
			nextSelectTypeRaw + "<>",
			getRawClassName(tableCount, valueCount));

		// constructor
		if (n > 0) {
			println("SelectChooser%d(%s other, int tableIndex) { super(other, tableIndex); }", n, SelectChooserBase.class.getSimpleName());
		} else {
			println("SelectChooser%d() { /* non-public */ }", n);
		}

		endClass();
	}

	private void printJoinFunctions(String resultType, String joinType, String joinFunction) {

		String ctorCall = String.format("%s<>(%s)", getRawClassName(tableCount + 1, valueCount), valueCount > 0? "other, expression" : "");

		// the general join(ISqlTable) method
		beginMethod("public <T%s> %s %s(%s<T%s> table)", tableCount, resultType, joinFunction, ISqlTable.class.getSimpleName(), tableCount);
		println("return _join(new %s, table, JoinType.%s);", ctorCall, joinType);
		endMethod();

		// the join(ISqlForeignField) method
		beginMethod(
			"public <T%s> %s %s(%s<T%s, T%s, ?> foreignField)",
			tableCount,
			resultType,
			joinFunction,
			ISqlForeignRowField.class.getSimpleName(),
			tableCount - 1,
			tableCount);
		println("return %sOnTable%s(foreignField);", joinFunction, tableCount - 1);
		endMethod();

		// the joinOnTableX(ISqlForeignField) method
		addImport(ISqlForeignRowField.class);
		for (int i = 0; i < tableCount; ++i) {
			beginMethod(
				"public <T%s> %s %sOnTable%d(%s<T%s, T%s, ?> foreignField)",
				tableCount,
				resultType,
				joinFunction,
				i,
				ISqlForeignRowField.class.getSimpleName(),
				i,
				tableCount);
			println(
				"return %s(%s).on().t%s().t%s(%s);",
				joinFunction,
				"foreignField.getTargetField().getTable()",
				i,
				tableCount,
				"foreignField.isEqualToTargetField()");
			endMethod();
		}

		// the joinReverse(ISqlForeignField) method
		beginMethod(
			"public <T%s> %s %sReverse(%s<T%s, T%s, ?> foreignField)",
			tableCount,
			resultType,
			joinFunction,
			ISqlForeignRowField.class.getSimpleName(),
			tableCount,
			tableCount - 1);
		println("return %sReverseOnTable%s(foreignField);", joinFunction, tableCount - 1);
		endMethod();

		// the joinReverseOnTableX(ISqlForeignField) method
		addImport(ISqlForeignRowField.class);
		for (int i = 0; i < tableCount; ++i) {
			beginMethod(
				"public <T%s> %s %sReverseOnTable%d(%s<T%s, T%s, ?> foreignField)",
				tableCount,
				resultType,
				joinFunction,
				i,
				ISqlForeignRowField.class.getSimpleName(),
				tableCount,
				i);
			println(
				"return %s(%s).on().t%s().t%s(%s);",//
				joinFunction,
				"foreignField.getTable()",
				tableCount,
				i,
				"foreignField.isEqualToTargetField()");
			endMethod();
		}
	}

	private static String getDeclaredClassName(int tableCount, int valueCount) {

		return getRawClassName(tableCount, valueCount) + getSelectParameterList(tableCount, valueCount);
	}

	private static String getClassName(int tableCount, int valueCount) {

		return getRawClassName(tableCount, valueCount) + SqlCodeGenerationUtils.implode("T", tableCount, "V", valueCount);
	}

	private static String getRawClassName(int tableCount, int valueCount) {

		return String.format("SqlSelect%s_%s", tableCount, valueCount);
	}

	private static String getSelectParameterList(int tableCount, int valueCount) {

		return SqlCodeGenerationUtils
			.implode(SqlCodeGenerationUtils.getTableArgumentList("T", tableCount), SqlCodeGenerationUtils.getValueParameterList("V", valueCount));
	}
}
