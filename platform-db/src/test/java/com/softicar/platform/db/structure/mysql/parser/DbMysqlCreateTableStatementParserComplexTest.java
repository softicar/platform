package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class DbMysqlCreateTableStatementParserComplexTest extends AbstractTest {

	private static final String CREATE_TABLE_STATEMENT = new StringBuilder()//
		.append("CREATE TABLE `db`.`Foo` (")
		.append(" `id` int NOT NULL AUTO_INCREMENT,")
		.append(" `bar` int COMMENT '@ignore@',")
		.append(" `baz` int,")
		.append(" PRIMARY KEY (`id`),")
		.append(" KEY `bar` (`bar`),")
		.append(" KEY `baz` (`baz`),")
		.append(" KEY `barbaz` (`bar`, `baz`),")
		.append(" CONSTRAINT `bar` FOREIGN KEY (`bar`) REFERENCES `Bar` (`id`),")
		.append(" CONSTRAINT `baz` FOREIGN KEY (`baz`) REFERENCES `Baz` (`id`)")
		.append(" CONSTRAINT `barbaz` FOREIGN KEY (`bar`, `baz`) REFERENCES `Baz` (`a`, `b`)")
		.append(")")
		.toString();

	@Test
	public void testWithIgnoredColumns() {

		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(CREATE_TABLE_STATEMENT).parse();

		assertColumns(tableStructure, "id", "bar", "baz");
		assertKeys(tableStructure, null, "bar", "baz", "barbaz");
		assertKeyColumns(tableStructure.getKeys().get(3), "bar", "baz");
		assertForeignKeys(tableStructure, "bar", "baz", "barbaz");
	}

	private void assertColumns(IDbTableStructure tableStructure, String...expectedColumns) {

		List<String> columnNames = tableStructure//
			.getColumns()
			.stream()
			.map(IDbColumnStructure::getNameOrThrow)
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedColumns), columnNames);
	}

	private void assertKeys(IDbTableStructure tableStructure, String...expectedKeys) {

		List<String> columnNames = tableStructure//
			.getKeys()
			.stream()
			.map(key -> key.getName().orElse(null))
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedKeys), columnNames);
	}

	private void assertKeyColumns(IDbKeyStructure keyStructure, String...expectedColumns) {

		List<String> columnNames = keyStructure//
			.getColumns()
			.stream()
			.map(IDbColumnStructure::getNameOrThrow)
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedColumns), columnNames);
	}

	private void assertForeignKeys(IDbTableStructure tableStructure, String...expectedNames) {

		List<String> actualNames = tableStructure//
			.getForeignKeys()
			.stream()
			.map(key -> key.getName().orElse(null))
			.collect(Collectors.toList());
		assertEquals(Arrays.asList(expectedNames), actualNames);
	}
}
