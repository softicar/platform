package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.test.DbComplexTestObjectTableLiteral;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Test;

public class DbMysqlCreateTableStatementParserTest extends AbstractTest {

	private final String createTableStatement;
	private final DbTableName tableName;

	public DbMysqlCreateTableStatementParserTest() {

		this.createTableStatement = new DbComplexTestObjectTableLiteral().getCreateTableStatement();
		this.tableName = new DbComplexTestObjectTableLiteral().getTableName();
	}

	@Test
	public void testDatabaseAndTableName() {

		IDbTableStructure tableStructure = parse();

		assertEquals(tableName.getDatabaseName(), tableStructure.getTableName().getDatabaseName());
		assertEquals(tableName.getSimpleName(), tableStructure.getTableName().getSimpleName());
	}

	@Test
	public void testColumnRows() {

		IDbTableStructure tableStructure = parse();

		new ListAsserter<>(tableStructure.getColumns(), ColumnAsserter::new)//

			.nextEntry()
			.assertName("id")
			.assertFieldType(SqlFieldType.INTEGER)
			.assertNullable(false)
			.assertAutoIncrement(true)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NONE)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("fk")
			.assertFieldType(SqlFieldType.INTEGER)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NONE)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("long")
			.assertFieldType(SqlFieldType.LONG)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NONE)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("string")
			.assertFieldType(SqlFieldType.STRING)
			.assertMaxLength(255)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NORMAL)
			.assertDefaultValue("abc")
			.assertComment("someComment")

			.nextEntry()
			.assertName("stringWithCharacterSet")
			.assertFieldType(SqlFieldType.STRING)
			.assertMaxLength(128)
			.assertNullable(false)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet("someCharacterSet")
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NONE)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("enum")
			.assertFieldType(SqlFieldType.ENUM)
			.assertEnumValues("BIG", "MEDIUM", "SMALL", "TINY")
			.assertNullable(false)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet("someCharacterSet")
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NONE)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("unsignedInteger")
			.assertFieldType(SqlFieldType.INTEGER)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(false)
			.assertTimestamp(false)
			.assertUnsigned(true)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.NULL)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("dayTime")
			.assertFieldType(SqlFieldType.DAY_TIME)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(true)
			.assertTimestamp(false)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP)
			.assertDefaultValue(null)
			.assertComment(null)

			.nextEntry()
			.assertName("timestamp")
			.assertFieldType(SqlFieldType.DAY_TIME)
			.assertNullable(true)
			.assertAutoIncrement(false)
			.assertOnUpdateNow(true)
			.assertTimestamp(true)
			.assertUnsigned(false)
			.assertCharacterSet(null)
			.assertCollation(null)
			.assertDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP)
			.assertDefaultValue(null)
			.assertComment(null)

			.assertNoMoreEntries();
	}

	@Test
	public void testKeyRows() {

		IDbTableStructure tableStructure = parse();

		new ListAsserter<>(tableStructure.getKeys(), KeyRowAsserter::new)//

			.nextEntry()
			.assertType(DbKeyType.PRIMARY)
			.assertNoName()
			.assertColumns("id")

			.nextEntry()
			.assertType(DbKeyType.UNIQUE)
			.assertName("database$table$uniqueStringLong")
			.assertColumns("string", "long")
			.assertType(DbKeyType.UNIQUE)

			.nextEntry()
			.assertType(DbKeyType.INDEX)
			.assertName("database$table$indexLong")
			.assertColumns("long")
			.assertType(DbKeyType.INDEX)

			.assertNoMoreEntries();
	}

	/**
	 * TODO need a case for an FK to another database and table
	 */
	@Test
	public void testForeignKeyRows() {

		IDbTableStructure tableStructure = parse();

		new ListAsserter<>(tableStructure.getForeignKeys(), ForeignKeyRowAsserter::new)//

			.nextEntry()
			.assertName("fk_constraint")
			.assertColumnList("fk")
			.assertForeignDatabase("database")
			.assertForeignTable("table")
			.assertForeignColumnList("id")
			.assertOnDeleteAction(DbForeignKeyAction.RESTRICT)
			.assertOnUpdateAction(DbForeignKeyAction.CASCADE)

			.assertNoMoreEntries();
	}

	@Test
	public void testWithUsingClauseInKeys() {

		String createStatement = new StringBuilder()//
			.append("CREATE TABLE `db`.`Foo` (")
			.append("	`id` int NOT NULL AUTO_INCREMENT,")
			.append("	`bar` int NOT NULL,")
			.append("	`baz` int NOT NULL,")
			.append("	`text` VARCHAR(200) NOT NULL,")
			.append("	PRIMARY KEY (`id`),")
			.append("	UNIQUE KEY `barKey` (`bar`) USING BTREE,")
			.append("	UNIQUE KEY `bazKey` USING HASH (`baz`),")
			.append("	FULLTEXT KEY `textKey` (`text`)")
			.append(")")
			.toString();
		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(createStatement).parse();
		assertEquals(4, tableStructure.getKeys().size());
	}

	@Test
	public void testWithTableComment() {

		String createStatement = new StringBuilder()//
			.append("CREATE TABLE `db`.`Foo` (")
			.append("	`id` int NOT NULL AUTO_INCREMENT,")
			.append("	PRIMARY KEY (`id`),")
			.append(")")
			.append("ENGINE = 'innodb' COMMENT = 'this is a comment'")
			.toString();
		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(createStatement).parse();
		assertEquals("this is a comment", tableStructure.getComment());
	}

	@Test
	public void testWithEnumTableComment() {

		String createStatement = new StringBuilder()//
			.append("CREATE TABLE `db`.`Foo` (")
			.append("	`id` int NOT NULL AUTO_INCREMENT,")
			.append("	PRIMARY KEY (`id`),")
			.append(")")
			.append("COMMENT = '@enum@'")
			.toString();
		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(createStatement).parse();
		assertTrue(tableStructure.isEnumTable());
	}

	@Test
	public void testWithDefaultValueDefinitions() {

		String createStatement = new StringBuilder()//
			.append("CREATE TABLE `db`.`Foo` (")
			.append("	`id` int NOT NULL AUTO_INCREMENT,")
			.append("	`noneColumn` int NOT NULL,")
			.append("	`currentTimestampTokenColumn` DATETIME DEFAULT CURRENT_TIMESTAMP,")
			.append("	`currentTimestampFunctionColumn` DATETIME DEFAULT current_timestamp(),")
			.append("	`nullColumn` int NOT NULL DEFAULT NULL,")
			.append("	`positiveNumberStringColumn` int NOT NULL DEFAULT '1',")
			.append("	`negativeNumberStringColumn` int NOT NULL DEFAULT '-2',")
			.append("	`positiveNumberDigitColumn` int NOT NULL DEFAULT 3,")
			.append("	`negativeNumberDigitColumn` int NOT NULL DEFAULT -4,")
			.append("	PRIMARY KEY (`id`)")
			.append(")")
			.toString();
		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(createStatement).parse();

		var noneColumn = tableStructure.getColumnByPhysicalNameOrThrow("noneColumn");
		assertEquals(DbColumnDefaultType.NONE, noneColumn.getDefaultType());

		var currentTimestampTokenColumn = tableStructure.getColumnByPhysicalNameOrThrow("currentTimestampTokenColumn");
		assertEquals(DbColumnDefaultType.CURRENT_TIMESTAMP, currentTimestampTokenColumn.getDefaultType());

		var currentTimestampFunctionColumn = tableStructure.getColumnByPhysicalNameOrThrow("currentTimestampFunctionColumn");
		assertEquals(DbColumnDefaultType.CURRENT_TIMESTAMP, currentTimestampFunctionColumn.getDefaultType());

		var nullColumn = tableStructure.getColumnByPhysicalNameOrThrow("nullColumn");
		assertEquals(DbColumnDefaultType.NULL, nullColumn.getDefaultType());

		var positiveNumberStringColumn = tableStructure.getColumnByPhysicalNameOrThrow("positiveNumberStringColumn");
		assertEquals(DbColumnDefaultType.NORMAL, positiveNumberStringColumn.getDefaultType());
		assertEquals("1", positiveNumberStringColumn.getDefaultValue());

		var negativeNumberStringColumn = tableStructure.getColumnByPhysicalNameOrThrow("negativeNumberStringColumn");
		assertEquals(DbColumnDefaultType.NORMAL, negativeNumberStringColumn.getDefaultType());
		assertEquals("-2", negativeNumberStringColumn.getDefaultValue());

		var positiveNumberDigitColumn = tableStructure.getColumnByPhysicalNameOrThrow("positiveNumberDigitColumn");
		assertEquals(DbColumnDefaultType.NORMAL, positiveNumberDigitColumn.getDefaultType());
		assertEquals("3", positiveNumberDigitColumn.getDefaultValue());

		var negativeNumberDigitColumn = tableStructure.getColumnByPhysicalNameOrThrow("negativeNumberDigitColumn");
		assertEquals(DbColumnDefaultType.NORMAL, negativeNumberDigitColumn.getDefaultType());
		assertEquals("-4", negativeNumberDigitColumn.getDefaultValue());
	}

	@Test
	public void testWithOnUpdateDefinitions() {

		String createStatement = new StringBuilder()//
			.append("CREATE TABLE `db`.`Foo` (")
			.append("	`id` int NOT NULL AUTO_INCREMENT,")
			.append("	`currentTimestampTokenColumn` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,")
			.append("	`currentTimestampFunctionColumn` DATETIME DEFAULT NULL ON UPDATE current_timestamp(),")
			.append("	`otherColumn` int NOT NULL,")
			.append("	PRIMARY KEY (`id`)")
			.append(")")
			.toString();
		IDbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(createStatement).parse();

		var currentTimestampTokenColumn = tableStructure.getColumnByPhysicalNameOrThrow("currentTimestampTokenColumn");
		assertTrue(currentTimestampTokenColumn.isOnUpdateNow());

		var currentTimestampFunctionColumn = tableStructure.getColumnByPhysicalNameOrThrow("currentTimestampFunctionColumn");
		assertTrue(currentTimestampFunctionColumn.isOnUpdateNow());

		var otherColumn = tableStructure.getColumnByPhysicalNameOrThrow("otherColumn");
		assertFalse(otherColumn.isOnUpdateNow());
	}

	private IDbTableStructure parse() {

		return new DbMysqlCreateTableStatementParser(createTableStatement).parse();
	}

	private class ListAsserter<E, A extends EntryAsserter<A>> {

		private final List<E> list;
		private final Function<E, A> asserterFactory;
		private int index;

		public ListAsserter(List<E> list, Function<E, A> asserterFactory) {

			this.list = list;
			this.asserterFactory = asserterFactory;
			this.index = 0;
		}

		public A nextEntry() {

			A asserter = asserterFactory.apply(getNextEntry());
			asserter.setListAsserter(this);
			return asserter;
		}

		public void assertNoMoreEntries() {

			assertEquals("Found more entries than expected.", list.size(), index);
		}

		public int getIndex() {

			return index;
		}

		private E getNextEntry() {

			assertTrue("Found fewer entries than expected.", list.size() > index);
			E entry = list.get(index);
			this.index++;
			return entry;
		}
	}

	private class EntryAsserter<A extends EntryAsserter<A>> {

		private ListAsserter<?, A> listAsserter;

		public void setListAsserter(ListAsserter<?, A> listAsserter) {

			this.listAsserter = listAsserter;
		}

		public A nextEntry() {

			return listAsserter.nextEntry();
		}

		public void assertNoMoreEntries() {

			listAsserter.assertNoMoreEntries();
		}

		protected <X> void assertEqual(X first, X second) {

			assertEquals(getFailureMessage(), first, second);
		}

		private String getFailureMessage() {

			return String.format("Assertion failed for entry number %s.", listAsserter.getIndex());
		}
	}

	private class ColumnAsserter extends EntryAsserter<ColumnAsserter> {

		private final IDbColumnStructure columnStructure;

		public ColumnAsserter(IDbColumnStructure columnStructure) {

			this.columnStructure = columnStructure;
		}

		public ColumnAsserter assertName(String name) {

			assertEqual(name, columnStructure.getNameOrThrow());
			return this;
		}

		public ColumnAsserter assertFieldType(SqlFieldType type) {

			assertEqual(type, columnStructure.getFieldType());
			return this;
		}

		public ColumnAsserter assertNullable(boolean nullable) {

			assertEqual(nullable, columnStructure.isNullable());
			return this;
		}

		public ColumnAsserter assertAutoIncrement(boolean autoIncrement) {

			assertEqual(autoIncrement, columnStructure.isAutoIncrement());
			return this;
		}

		public ColumnAsserter assertOnUpdateNow(boolean onUpdateNow) {

			assertEqual(onUpdateNow, columnStructure.isOnUpdateNow());
			return this;
		}

		public ColumnAsserter assertTimestamp(boolean timestamp) {

			assertEqual(timestamp, columnStructure.isTimestamp());
			return this;
		}

		public ColumnAsserter assertUnsigned(boolean unsigned) {

			assertEqual(unsigned, columnStructure.isUnsigned());
			return this;
		}

		public ColumnAsserter assertCharacterSet(String characterSet) {

			assertEqual(characterSet, columnStructure.getCharacterSet());
			return this;
		}

		public ColumnAsserter assertCollation(String collation) {

			assertEqual(collation, columnStructure.getCollation());
			return this;
		}

		public ColumnAsserter assertDefaultType(DbColumnDefaultType defaultType) {

			assertEqual(defaultType, columnStructure.getDefaultType());
			return this;
		}

		public ColumnAsserter assertDefaultValue(String defaultValue) {

			assertEqual(defaultValue, columnStructure.getDefaultValue());
			return this;
		}

		public ColumnAsserter assertMaxLength(int maxLength) {

			assertEqual(maxLength, columnStructure.getMaxLength());
			return this;
		}

		public ColumnAsserter assertEnumValues(String...enumValues) {

			assertEqual(Arrays.asList(enumValues), columnStructure.getEnumValues());
			return this;
		}

		public ColumnAsserter assertComment(String comment) {

			assertEqual(comment, columnStructure.getComment());
			return this;
		}
	}

	private class KeyRowAsserter extends EntryAsserter<KeyRowAsserter> {

		private final IDbKeyStructure keyStructure;

		public KeyRowAsserter(IDbKeyStructure keyStructure) {

			this.keyStructure = keyStructure;
		}

		public KeyRowAsserter assertType(DbKeyType type) {

			assertEqual(type, keyStructure.getType());
			return this;
		}

		public KeyRowAsserter assertName(String name) {

			assertEqual(name, keyStructure.getNameOrThrow());
			return this;
		}

		public KeyRowAsserter assertNoName() {

			assertFalse(keyStructure.getName().isPresent());
			return this;
		}

		public KeyRowAsserter assertColumns(String...columns) {

			List<String> actualColumns = keyStructure//
				.getColumns()
				.stream()
				.map(IDbColumnStructure::getNameOrThrow)
				.collect(Collectors.toList());
			assertEqual(Arrays.asList(columns), actualColumns);
			return this;
		}
	}

	private class ForeignKeyRowAsserter extends EntryAsserter<ForeignKeyRowAsserter> {

		private final IDbForeignKeyStructure foreignKeyStructure;

		public ForeignKeyRowAsserter(IDbForeignKeyStructure foreignKeyStructure) {

			this.foreignKeyStructure = foreignKeyStructure;
		}

		public ForeignKeyRowAsserter assertName(String name) {

			assertEqual(name, foreignKeyStructure.getNameOrThrow());
			return this;
		}

		public ForeignKeyRowAsserter assertColumnList(String...columnList) {

			assertEqual(Arrays.asList(columnList), foreignKeyStructure.getColumns());
			return this;
		}

		public ForeignKeyRowAsserter assertForeignDatabase(String foreignDatabase) {

			assertEqual(foreignDatabase, foreignKeyStructure.getForeignTableName().getDatabaseName());
			return this;
		}

		public ForeignKeyRowAsserter assertForeignTable(String foreignTable) {

			assertEqual(foreignTable, foreignKeyStructure.getForeignTableName().getSimpleName());
			return this;
		}

		public ForeignKeyRowAsserter assertForeignColumnList(String...foreignColumnList) {

			assertEqual(Arrays.asList(foreignColumnList), foreignKeyStructure.getForeignColumns());
			return this;
		}

		public ForeignKeyRowAsserter assertOnDeleteAction(DbForeignKeyAction onDeleteAction) {

			assertEqual(onDeleteAction, foreignKeyStructure.getOnDeleteAction());
			return this;
		}

		public ForeignKeyRowAsserter assertOnUpdateAction(DbForeignKeyAction onUpdateAction) {

			assertEqual(onUpdateAction, foreignKeyStructure.getOnUpdateAction());
			return this;
		}
	}
}
