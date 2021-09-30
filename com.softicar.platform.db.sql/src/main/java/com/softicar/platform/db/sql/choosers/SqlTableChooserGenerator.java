package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.db.sql.AbstractSqlJavaCodePrinter;
import com.softicar.platform.db.sql.SqlGenerator;
import com.softicar.platform.db.sql.SqlMasterGenerator;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.generation.SqlCodeGenerationUtils;
import com.softicar.platform.db.sql.generation.SqlJavaClassName;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public class SqlTableChooserGenerator extends AbstractSqlJavaCodePrinter {

	public static final int MAX_EXPRESSION = 4;
	private final int tableCount;
	private final int expressionTableCount;
	private final SqlJavaClassName className;

	public static void generateAll() {

		// select choosers
		for (int i = 1; i <= SqlMasterGenerator.MAX_TABLE_COUNT; ++i) {
			for (int j = 0; j < SqlGenerator.MAX_EXPRESSION_TABLE_COUNT; ++j) {
				new SqlTableChooserGenerator(i, j).generateAndWriteOut();
			}
		}
	}

	private void generateAndWriteOut() {

		generate();
		writeOutToSourceFolder(className.getRawName());
	}

	private SqlTableChooserGenerator(int tableCount, int expressionTableCount) {

		this.tableCount = tableCount;
		this.expressionTableCount = expressionTableCount;
		this.className = SqlCodeGenerationUtils.getTableChooserName(tableCount, expressionTableCount);
	}

	private void generate() {

		addImport(ISqlSelectCoreAdapter.class);
		beginClass("public final class %s extends %s<S>", className.getFullDeclaredType(), AbstractSqlTableChooser.class.getSimpleName());

		// T0(expression), T1(expression), ... functions
		if (expressionTableCount < SqlGenerator.MAX_EXPRESSION_TABLE_COUNT) {
			String expressionRaw = "ISqlExpression" + (expressionTableCount + 1);
			addImport(new JavaPackageName(ISqlExpression.class).getClassName(expressionRaw));
			for (int i = 0; i < tableCount; ++i) {
				beginMethod(
					"public S t%d(%s%s expression)",
					i,
					expressionRaw,
					SqlCodeGenerationUtils.implode("? extends V", "E", expressionTableCount, "T" + i));
				println("return addTable(%d).addExpression(expression);", i);
				endMethod();
			}
		}

		// T0(), T1(), ... functions
		if (expressionTableCount < SqlGenerator.MAX_EXPRESSION_TABLE_COUNT - 1) {
			SqlJavaClassName next = SqlCodeGenerationUtils.getTableChooserName(tableCount, expressionTableCount + 1);
			for (int i = 0; i < tableCount; ++i) {
				String nextType = String.format("%s<%s, T%d>", next.getRawName(), className.getArguments(), i);
				beginMethod("public %s t%d()", nextType, i);
				println("return new %s<>(this, %d);", next.getRawName(), i);
				endMethod();
			}
		}

		// constructor
		if (expressionTableCount > 0) {
			beginMethod("%s(%s<S> chooser, int tableIndex)", className.getRawName(), AbstractSqlTableChooser.class.getSimpleName());
			println("super(chooser, tableIndex);");
			endMethod();
		} else {
			addImport(PartType.class);
			beginMethod("public %s(S select, PartType partType)", className.getRawName());
			println("super(select, partType);");
			endMethod();
		}
		endClass();
	}
}
