package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.example.SqlExampleDelivery;
import org.junit.Assert;
import org.junit.Test;

public class SqlTableChooserTest extends Assert {

	@Test
	public void testChooserOnSingleTable() {

		String sql = Sql//
			.from(SqlExampleDelivery.TABLE)
			.where()
			.t0(SqlExampleDelivery.SUPPLIER.isNotNull())
			.select()
			.t0(SqlExampleDelivery.NUMBER)
			.groupBy()
			.t0(SqlExampleDelivery.SUPPLIER)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t0.`number` ")
			.append("FROM `example`.`delivery` t0 ")
			.append("WHERE (t0.`supplier` IS NOT NULL) ")
			.append("GROUP BY t0.`supplier`")
			.toString();

		assertEquals(expectedSql, sql);
	}
}
