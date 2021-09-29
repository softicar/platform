package com.softicar.platform.db.sql.expressions.base;

import com.softicar.platform.common.code.java.IdentifierNames;
import com.softicar.platform.common.code.java.JavaClass;
import com.softicar.platform.common.code.java.JavaCodePrinter;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlCastMap;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionClassFetcher;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;
import com.softicar.platform.db.sql.generation.SqlCodeGenerationUtils;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlExpressionGenerator extends JavaCodePrinter {

	private static final SqlCastMap castMap = new SqlCastMap();
	private final int tableCount;
	private final Class<?> valueClass;

	public AbstractSqlExpressionGenerator(int tableCount, Class<?> valueClass) {

		this.tableCount = tableCount;
		this.valueClass = valueClass;
	}

	protected abstract void printInterfaceMethods();

	protected String getTableCountDependentSuperInterfaceType() {

		JavaClassName className = new JavaClassName(ISqlExpression.class.getCanonicalName() + getTableCount());
		addImport(className);
		return className.getSimpleName() + SqlCodeGenerationUtils.implode(valueClass.getSimpleName(), "T", getTableCount());
	}

	// -------------------- getter methods -------------------- //

	public int getTableCount() {

		return tableCount;
	}

	public boolean isBool() {

		return valueClass == Boolean.class;
	}

	public boolean isDecimal() {

		return valueClass == Float.class || valueClass == Double.class || valueClass == BigDecimal.class;
	}

	public boolean isIntegral() {

		return valueClass == Integer.class || valueClass == Long.class;
	}

	// -------------------- generation methods -------------------- //

	public void generateInterface() {

		// collect super interfaces
		List<String> superInterfaces = new ArrayList<>();
		if (isBool() && getTableCount() == 1) {
			addImport(ISqlBooleanExpression.class);
			superInterfaces.add(ISqlBooleanExpression.class.getSimpleName() + "<T0>");
		} else {
			superInterfaces.add(getExpressionInterfaceType());
		}
		if (!isBool()) {
			if (getTableCount() == 1) {
				addImport(ISqlBooleanExpression.class);
				superInterfaces.add(ISqlBooleanExpression.class.getSimpleName() + "<T0>");
			} else {
				superInterfaces.add(getExpressionInterfaceType(Boolean.class));
			}
		}
		if (isDecimal()) {
			superInterfaces.add(getExpressionInterfaceType(Long.class));
		}

		// print class
		beginBlock(
			"public interface %s extends %s, %s<%s>",
			getExpressionInterfaceType(),
			getTableCountDependentSuperInterfaceType(),
			new SqlExpressionClassFetcher(valueClass).getBaseInterface().getSimpleName(),
			Imploder.implode(superInterfaces, ", "));
		printInterfaceMethods();
		endBlock();

		// write file
		writeOutToSourceFolder(getExpressionInterface(valueClass, getTableCount()).getName());
	}

	public void generateClass() {

		addImport(SqlExpressionWrapper.class);
		beginBlock(
			"public class %s extends %s<%s> implements %s",
			getExpressionClassType(valueClass, getTableCount()),
			SqlExpressionWrapper.class.getSimpleName(),
			valueClass.getSimpleName(),
			getExpressionInterfaceType());
		printConstructor();
		endBlock();
		writeOutToSourceFolder(getExpressionClass(valueClass, getTableCount()).getName());
	}

	// -------------------- public printing methods -------------------- //

	public void printFactoryMethods() {

		printWrapMethod();
		if (isDecimal()) {
			printWrapLongMethod();
		}
	}

	public void printCastOperation(Class<?> resultValueClass) {

		if (resultValueClass != valueClass) {
			String cast = castMap.getCast(resultValueClass);
			String operation = cast.equals(castMap.getCast(valueClass))? "NOP" : "CAST_" + cast;
			printUnaryOperation(resultValueClass, "castTo" + resultValueClass.getSimpleName(), operation);
		}
	}

	public void printEqualityOperations(int otherTableCount) {

		printBinaryOperation(Boolean.class, "isEqual", "IS_EQUAL", otherTableCount);
		printBinaryOperation(Boolean.class, "isNotEqual", "IS_NOT_EQUAL", otherTableCount);
	}

	public void printComparisonOperations(int otherTableCount) {

		printBinaryOperation(Boolean.class, "isLess", "IS_LESS", otherTableCount);
		printBinaryOperation(Boolean.class, "isLessEqual", "IS_LESS_EQUAL", otherTableCount);
		printBinaryOperation(Boolean.class, "isGreater", "IS_GREATER", otherTableCount);
		printBinaryOperation(Boolean.class, "isGreaterEqual", "IS_GREATER_EQUAL", otherTableCount);
	}

	public void printUnaryOperation(Class<?> resultValueClass, String methodName, String operationName) {

		addImport(SqlOperations.class);
		addImport(SqlValueTypes.class);
		beginBlock("default %s %s()", getExpressionInterfaceType(resultValueClass), methodName);
		println(
			"return new %s(SqlOperations.%s.create(%s.%s, this));",
			getExpressionClassTypeWithDiamond(resultValueClass, getTableCount()),
			operationName,
			SqlValueTypes.class.getSimpleName(),
			IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get(resultValueClass.getSimpleName()));
		endBlock();
	}

	public void printBinaryOperation(String methodName, String operationName, int otherTableCount) {

		printBinaryOperation(valueClass, methodName, operationName, otherTableCount);
	}

	public void printBinaryOperation(Class<?> resultValueClass, String methodName, String operationName, int otherTableCount) {

		String resultExpressionType = getExpressionInterfaceType(resultValueClass, getTableCount(), otherTableCount);
		String resultExpressionTypeDiamond = getExpressionClassTypeWithDiamond(resultValueClass, getTableCount(), otherTableCount);

		addImport(SqlOperations.class);
		beginBlock(
			"default %s %s %s(%s other)",
			SqlCodeGenerationUtils.implode("S%d", otherTableCount),
			resultExpressionType,
			methodName,
			getMinimumExpressionInterfaceType(valueClass, "S", otherTableCount));
		if (resultValueClass == valueClass) {
			println("return new %s(SqlOperations.%s.create(this, other));", resultExpressionTypeDiamond, operationName);
		} else {
			addImport(SqlValueTypes.class);
			println(
				"return new %s(SqlOperations.%s.create(%s.%s, this, other));",
				resultExpressionTypeDiamond,
				operationName,
				SqlValueTypes.class.getSimpleName(),
				IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get(resultValueClass.getSimpleName()));
		}
		endBlock();
	}

	// -------------------- private printing methods -------------------- //

	private void printConstructor() {

		addImport(ISqlExpression.class);
		beginBlock("public %s(ISqlExpression<%s> expression)", getExpressionClass(valueClass, getTableCount()).getSimpleName(), valueClass.getSimpleName());
		println("super(expression);");
		endBlock();
	}

	private void printWrapMethod() {

		addImport(ISqlExpression.class);
		println("@Override");
		beginBlock("default %s wrap(ISqlExpression<%s> expression)", getExpressionInterfaceType(), valueClass.getSimpleName());
		println("return new %s(expression);", getExpressionClassTypeWithDiamond(valueClass, getTableCount()));
		endBlock();
	}

	private void printWrapLongMethod() {

		addImport(ISqlExpression.class);
		println("@Override");
		beginBlock("default %s wrapLong(ISqlExpression<Long> expression)", getExpressionInterfaceType(Long.class));
		println("return new %s(expression);", getExpressionClassTypeWithDiamond(Long.class, getTableCount()));
		endBlock();
	}

	// -------------------- expression interface type -------------------- //

	private String getExpressionInterfaceType() {

		return getExpressionInterfaceType(valueClass, getTableCount());
	}

	private String getExpressionInterfaceType(Class<?> valueClass) {

		return getExpressionInterfaceType(valueClass, getTableCount());
	}

	private String getExpressionInterfaceType(Class<?> valueClass, int tableCount) {

		return getExpressionInterface(valueClass, tableCount).getSimpleName() + SqlCodeGenerationUtils.implode("T", tableCount);
	}

	private String getMinimumExpressionInterfaceType(Class<?> valueClass, String param, int tableCount) {

		return getExpressionInterface(ISqlExpression.class, tableCount).getSimpleName()
				+ SqlCodeGenerationUtils.implode(valueClass.getSimpleName(), param, tableCount);
	}

	private String getExpressionInterfaceType(Class<?> valueClass, int tableCount, int otherTableCount) {

		return getExpressionInterface(valueClass, tableCount + otherTableCount).getSimpleName()
				+ SqlCodeGenerationUtils.implode("T", tableCount, "S", otherTableCount);
	}

	private JavaClass getExpressionInterface(Class<?> valueClass, int tableCount) {

		JavaClass expressionInterface = new SqlExpressionClassFetcher(valueClass).getInterface(tableCount);
		addImport(expressionInterface);
		return expressionInterface;
	}

	// -------------------- expression class type -------------------- //

	private String getExpressionClassType(Class<?> valueClass, int tableCount) {

		return getExpressionClass(valueClass, tableCount).getSimpleName() + SqlCodeGenerationUtils.implode("T", tableCount);
	}

	private String getExpressionClassTypeWithDiamond(Class<?> valueClass, int tableCount, int otherTableCount) {

		return getExpressionClassTypeWithDiamond(valueClass, tableCount + otherTableCount);
	}

	private String getExpressionClassTypeWithDiamond(Class<?> valueClass, int tableCount) {

		return getExpressionClass(valueClass, tableCount).getSimpleName() + (tableCount > 0? "<>" : "");
	}

	private JavaClass getExpressionClass(Class<?> valueClass, int tableCount) {

		JavaClass expressionClass = new SqlExpressionClassFetcher(valueClass).getImplementingClass(tableCount);
		addImport(expressionClass);
		return expressionClass;
	}
}
