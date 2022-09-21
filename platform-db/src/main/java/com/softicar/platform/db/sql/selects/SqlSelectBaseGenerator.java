package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.AbstractTuple;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.AbstractSqlJavaCodePrinter;
import com.softicar.platform.db.sql.SqlMasterGenerator;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.generation.SqlCodeGenerationUtils;

public class SqlSelectBaseGenerator extends AbstractSqlJavaCodePrinter {

	private final int valueCount;
	private final String tupleType;
	private final String otherType;
	private final String expressionType;

	public static void generateAll() {

		for (int i = 1; i <= SqlMasterGenerator.MAX_VALUE_COUNT; ++i) {
			SqlSelectBaseGenerator generator = new SqlSelectBaseGenerator(i);
			generator.generate();
			generator.writeOutToSourceFolder("SqlSelectBase" + i);
		}
	}

	private SqlSelectBaseGenerator(int valueCount) {

		this.valueCount = valueCount;
		this.tupleType = getTupleType();
		this.otherType = getOtherType();
		this.expressionType = getExpressionType();
	}

	private String getOtherType() {

		return String.format("SqlSelectBase%d%s", valueCount - 1, SqlCodeGenerationUtils.implode("V", valueCount - 1));
	}

	private String getExpressionType() {

		addImport(ISqlExpression.class);
		return String.format("%s<V%d>", ISqlExpression.class.getSimpleName(), valueCount - 1);
	}

	private String getTupleType() {

		switch (valueCount) {
		case 1:
			return "V0";
		default:
			addImport(new JavaPackageName(AbstractTuple.class).getClassName("Tuple" + valueCount));
			return "Tuple" + valueCount + SqlCodeGenerationUtils.implode("V", valueCount);
		}
	}

	private void generate() {

		addImport(AbstractSqlSelect.class);
		String type = String.format("SqlSelectBase%d%s", valueCount, SqlCodeGenerationUtils.implode("V", valueCount));
		String base = String.format("%s<%s>", AbstractSqlSelect.class.getSimpleName(), tupleType);
		beginClass("abstract class %s extends %s", type, base);
		printFields();
		println();
		printConstructor();
		printGetResultRowMethod();
		endClass();
	}

	private void printFields() {

		println("protected final %s other;", otherType);
		println("protected final %s expression;", expressionType);
		println("protected final int columnIndex;");
	}

	private void printConstructor() {

		beginMethod("protected SqlSelectBase%d(%s other, %s expression)", valueCount, otherType, expressionType);
		println("this.other = other;");
		println("this.expression = expression;");
		if (valueCount > 1) {
			println("this.columnIndex = other.columnIndex + other.expression.getColumnCount();");
		} else {
			println("this.columnIndex = 1;");
		}
		endMethod();
	}

	private void printGetResultRowMethod() {

		println("@Override");
		addImport(DbResultSet.class);
		beginMethod("public %s getResultRow(%s resultSet)", tupleType, DbResultSet.class.getSimpleName());
		if (valueCount == 1) {
			println("return expression.getValueType().getValue(resultSet, columnIndex);");
		} else {
			println("V%s value = expression.getValueType().getValue(resultSet, columnIndex);", valueCount - 1);
			println("return new Tuple%s<>(other.getResultRow(resultSet), value);", valueCount);
		}
		endMethod();
	}
}
