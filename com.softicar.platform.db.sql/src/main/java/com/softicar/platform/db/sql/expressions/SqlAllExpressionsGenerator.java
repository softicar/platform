package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.expressions.array.SqlByteArrayExpressionGenerator;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpressionGenerator;
import com.softicar.platform.db.sql.expressions.date.SqlDateExpressionGenerator;
import com.softicar.platform.db.sql.expressions.numeric.SqlNumericExpressionGenerator;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpressionGenerator;

public class SqlAllExpressionsGenerator {

	public static void main(String[] args) {

		SqlByteArrayExpressionGenerator.generateAll();
		SqlBooleanExpressionGenerator.generateAll();
		SqlDateExpressionGenerator.generateAll();
		SqlNumericExpressionGenerator.generateAll();
		SqlStringExpressionGenerator.generateAll();
	}
}
