package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

public class DbMysqlInsertStatementParserTest extends Assert {

	private final IDbColumnStructure idColumnStructure;
	private final IDbColumnStructure stringColumnStructure;
	private final IDbColumnStructure doubleColumnStructure;

	public DbMysqlInsertStatementParserTest() {

		DbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(getCreateTableStatement()).parse();
		this.idColumnStructure = tableStructure.getColumnByPhysicalName("id").get();
		this.stringColumnStructure = tableStructure.getColumnByPhysicalName("stringColumn").get();
		this.doubleColumnStructure = tableStructure.getColumnByPhysicalName("doubleColumn").get();
	}

	@Test
	public void testParse() {

		String insertStatement = "INSERT INTO `someDatabase`.`someTable` (`id`, `stringColumn`, `doubleColumn`) VALUES (1, 'foo', 12.34), (2, 'bar', NULL);";

		DbMysqlParsedInsertStatement parserResult = new DbMysqlInsertStatementParser(insertStatement).parse();

		new Asserter(parserResult)//
			.nextRow()
			.assertRowValueCount(3)
			.assertRowValue(idColumnStructure, "1")
			.assertRowValue(stringColumnStructure, "foo")
			.assertRowValue(doubleColumnStructure, "12.34")
			.nextRow()
			.assertRowValueCount(3)
			.assertRowValue(idColumnStructure, "2")
			.assertRowValue(stringColumnStructure, "bar")
			.assertRowValue(doubleColumnStructure, "NULL")
			.assertNoMoreRows();
	}

	private String getCreateTableStatement() {

		return new StringBuilder()//
			.append("CREATE TABLE `someDatabase`.`someTable` (\n")
			.append("`id` INT NOT NULL AUTO_INCREMENT,\n")
			.append("`stringColumn` VARCHAR(32),\n")
			.append("`doubleColumn` DOUBLE,\n")
			.append("PRIMARY KEY(`id`)\n")
			.append(");\n")
			.toString();
	}

	private class Asserter {

		private List<String> currentRow;
		private final Iterator<List<String>> rowIterator;
		private final Map<String, Integer> columnIndexMap;

		public Asserter(DbMysqlParsedInsertStatement parserResult) {

			this.currentRow = null;

			this.columnIndexMap = new TreeMap<>();
			List<String> columnNames = parserResult.getColumnNames();
			for (int i = 0; i < columnNames.size(); i++) {
				columnIndexMap.put(columnNames.get(i), i);
			}

			this.rowIterator = parserResult.getValueRows().iterator();
		}

		public Asserter nextRow() {

			this.currentRow = rowIterator.next();
			return this;
		}

		public Asserter assertRowValueCount(int expectedCount) {

			assertEquals(expectedCount, currentRow.size());
			return this;
		}

		public Asserter assertRowValue(IDbColumnStructure columnStructure, String expectedValue) {

			Integer columnIndex = columnIndexMap.get(columnStructure.getNameOrThrow());
			String value = currentRow.get(columnIndex);
			assertEquals(expectedValue, value);
			return this;
		}

		public void assertNoMoreRows() {

			assertFalse(rowIterator.hasNext());
		}
	}
}
