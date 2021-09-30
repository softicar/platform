package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.db.runtime.record.DbTestRecord;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableKeyIsInExpressionTest extends AbstractDbTest {

	@Test
	public void testWithEmptyList() {

		String select = DbTestRecord.TABLE.createSelect().where(new DbTableKeyIsInExpression<>(DbTestRecord.TABLE)).getSelectText();
		assertEquals("SELECT t.`name`, t.`index`, t.`address` FROM `db`.`foo` t WHERE FALSE", select);
	}

	@Test
	public void testWithRowValueExpressionSupport() {

		String select = DbTestRecord.TABLE
			.createSelect()
			.where(new DbTableKeyIsInExpression<>(DbTestRecord.TABLE).addKeyValue(new Tuple2<>("foo", 33)).addKeyValue(new Tuple2<>("bar", 44)))
			.getSelectText();
		assertEquals("SELECT t.`name`, t.`index`, t.`address` FROM `db`.`foo` t WHERE ((t.`name`,t.`index`) IN ((?,?),(?,?)))", select);
	}

	@Test
	public void testWithoutRowValueExpressionSupport() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MSSQL_SERVER_2000)
				.build())) {

			String select = DbTestRecord.TABLE
				.createSelect()
				.where(new DbTableKeyIsInExpression<>(DbTestRecord.TABLE).addKeyValue(new Tuple2<>("foo", 33)).addKeyValue(new Tuple2<>("bar", 44)))
				.getSelectText();

			assertEquals(
				new StringBuilder()
					.append("SELECT t.[name], t.[index], t.[address] ")
					.append("FROM [db].[foo] t ")
					.append("WHERE (((t.[name] = ?) AND (t.[index] = ?)) OR ((t.[name] = ?) AND (t.[index] = ?)))")
					.toString(),
				select);
		}
	}
}
