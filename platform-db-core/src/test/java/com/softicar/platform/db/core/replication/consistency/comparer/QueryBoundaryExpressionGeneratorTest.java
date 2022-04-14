package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.replication.consistency.comparer.QueryBoundaryExpressionGenerator.Mode;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.definition.DbTableKeyDefinition;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class QueryBoundaryExpressionGeneratorTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final String X = "X";
	private static final String Y = "Y";
	private static final String Z = "Z";
	private static final List<String> COLUMNS = Arrays.asList(A, B, C);
	private static final List<String> VALUES = Arrays.asList(X, Y, Z);
	private static final TableKeyRow TABLE_KEY_ROW = new TableKeyRow(new DbTableKeyDefinition(COLUMNS), VALUES);

	@Test
	public void testWithInclusiveLowerBound() {

		DbStatement expression = new QueryBoundaryExpressionGenerator(TABLE_KEY_ROW, Mode.INCLUSIVE_LOWER_BOUND).generate();

		assertEquals(
			new StringBuilder()//
				.append("(A = ? AND B = ? AND C >= ?)")
				.append(" OR ")
				.append("(A = ? AND B > ?)")
				.append(" OR ")
				.append("(A > ?)")
				.toString(),
			expression.getText());

		assertEquals(Arrays.asList(X, Y, Z, X, Y, X), expression.getParameters());
	}

	@Test
	public void testWithExclusiveLowerBound() {

		DbStatement expression = new QueryBoundaryExpressionGenerator(TABLE_KEY_ROW, Mode.EXCLUSIVE_LOWER_BOUND).generate();

		assertEquals(
			new StringBuilder()//
				.append("(A = ? AND B = ? AND C > ?)")
				.append(" OR ")
				.append("(A = ? AND B > ?)")
				.append(" OR ")
				.append("(A > ?)")
				.toString(),
			expression.getText());

		assertEquals(Arrays.asList(X, Y, Z, X, Y, X), expression.getParameters());
	}

	@Test
	public void testWithInclusiveUpperBound() {

		DbStatement expression = new QueryBoundaryExpressionGenerator(TABLE_KEY_ROW, Mode.INCLUSIVE_UPPER_BOUND).generate();

		assertEquals(
			new StringBuilder()//
				.append("(A = ? AND B = ? AND C <= ?)")
				.append(" OR ")
				.append("(A = ? AND B < ?)")
				.append(" OR ")
				.append("(A < ?)")
				.toString(),
			expression.getText());

		assertEquals(Arrays.asList(X, Y, Z, X, Y, X), expression.getParameters());
	}

	@Test
	public void testWithExclusiveUpperBound() {

		DbStatement expression = new QueryBoundaryExpressionGenerator(TABLE_KEY_ROW, Mode.EXCLUSIVE_UPPER_BOUND).generate();

		assertEquals(
			new StringBuilder()//
				.append("(A = ? AND B = ? AND C < ?)")
				.append(" OR ")
				.append("(A = ? AND B < ?)")
				.append(" OR ")
				.append("(A < ?)")
				.toString(),
			expression.getText());

		assertEquals(Arrays.asList(X, Y, Z, X, Y, X), expression.getParameters());
	}
}
