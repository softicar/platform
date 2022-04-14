package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Test;

public class InternalDbMysqlColumnScaleDeterminerTest extends AbstractTest {

	@Test
	public void test() {

		assertScale(0, "`x` DECIMAL(5)");
		assertScale(5, "`x` DECIMAL(7,5)");
	}

	private void assertScale(int expectedScale, String columnDefinition) {

		String sql = String.format("CREATE TABLE `foo` (%s)", columnDefinition);
		IDbColumnStructure columnStructure = new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
		assertEquals(expectedScale, columnStructure.getScale());
	}
}
