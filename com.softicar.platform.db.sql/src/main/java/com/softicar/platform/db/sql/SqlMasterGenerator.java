package com.softicar.platform.db.sql;

import com.softicar.platform.db.sql.choosers.SqlTableChooserGenerator;
import com.softicar.platform.db.sql.expressions.array.SqlByteArrayExpressionGenerator;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpressionGenerator;
import com.softicar.platform.db.sql.expressions.date.SqlDateExpressionGenerator;
import com.softicar.platform.db.sql.expressions.numeric.SqlNumericExpressionGenerator;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpressionGenerator;
import com.softicar.platform.db.sql.selects.SqlSelectBaseGenerator;
import com.softicar.platform.db.sql.selects.SqlSelectGenerator;

public class SqlMasterGenerator {

	public static final int MAX_TABLE_COUNT = 8;
	public static final int MAX_VALUE_COUNT = 8;

	public static void main(String[] args) {

		SqlGenerator.generateAll();
		SqlSelectGenerator.generateAll();
		SqlSelectBaseGenerator.generateAll();
		SqlTableChooserGenerator.generateAll();

		SqlByteArrayExpressionGenerator.generateAll();
		SqlBooleanExpressionGenerator.generateAll();
		SqlDateExpressionGenerator.generateAll();
		SqlNumericExpressionGenerator.generateAll();
		SqlStringExpressionGenerator.generateAll();
	}
}
