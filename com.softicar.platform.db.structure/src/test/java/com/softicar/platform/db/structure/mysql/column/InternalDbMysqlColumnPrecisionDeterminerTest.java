package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Assert;
import org.junit.Test;

public class InternalDbMysqlColumnPrecisionDeterminerTest extends Assert {

	@Test
	public void test() {

		assertPrecision(5, "`x` DECIMAL(5)");
		assertPrecision(7, "`x` DECIMAL(7,5)");
	}

	private void assertPrecision(int expectedPrecision, String columnDefinition) {

		String sql = String.format("CREATE TABLE `foo` (%s)", columnDefinition);
		IDbColumnStructure columnStructure = new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
		assertEquals(expectedPrecision, columnStructure.getPrecision());
	}
}
