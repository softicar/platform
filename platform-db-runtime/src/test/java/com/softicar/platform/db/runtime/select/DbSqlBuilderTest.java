package com.softicar.platform.db.runtime.select;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.Arrays;
import org.junit.Test;

public class DbSqlBuilderTest extends AbstractTest {

	private final DbSqlBuilder builder;

	public DbSqlBuilderTest() {

		this.builder = new DbSqlBuilder();
	}

	@Test
	public void testWHERE() {

		builder.WHERE();
		builder.WHERE();

		assertEquals(Arrays.asList(SqlKeyword.WHERE, SqlKeyword.AND), builder.getSelect().getTokens());
	}

	@Test
	public void testGROUP_BY() {

		builder.GROUP_BY();
		builder.GROUP_BY();

		assertEquals(Arrays.asList(SqlKeyword.GROUP, SqlKeyword.BY, SqlSymbol.COMMA), builder.getSelect().getTokens());
	}

	@Test
	public void testHAVING() {

		builder.HAVING();
		builder.HAVING();

		assertEquals(Arrays.asList(SqlKeyword.HAVING, SqlKeyword.AND), builder.getSelect().getTokens());
	}

	@Test
	public void testORDER_BY() {

		builder.ORDER_BY();
		builder.ORDER_BY();

		assertEquals(Arrays.asList(SqlKeyword.ORDER, SqlKeyword.BY, SqlSymbol.COMMA), builder.getSelect().getTokens());
	}

	@Test
	public void testStraighJoin() {

		builder.setStraightJoin(true);
		builder.SELECT();
		builder.SELECT();

		assertEquals(Arrays.asList(SqlKeyword.SELECT, SqlKeyword.STRAIGHT_JOIN, SqlSymbol.COMMA), builder.getSelect().getTokens());
	}
}
