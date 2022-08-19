package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.test.DbComplexTestObjectTableLiteral;
import java.util.Arrays;
import org.junit.Test;

public class DbTableStructureSqlGeneratorTest extends AbstractTest {

	private static final int AUTO_INCREMENT = 252;

	@Test
	public void testGetCreateTableStatementWithForeignKeys() {

		String expectedStatement = new StringBuilder()
			.append("CREATE TABLE `database`.`table` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT + "),")
			.append("`fk` INT,")
			.append("PRIMARY KEY (`id`),")
			.append("CONSTRAINT `fk_constraint` FOREIGN KEY (`fk`) REFERENCES `database`.`table` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE);")
			.toString();

		DbTableStructure structure = new TestTableStructureWithForeignKeys();
		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setAutoIncrement(AUTO_INCREMENT)
			.getCreateTableStatement();

		assertEquals(expectedStatement, actualStatement);
	}

	@Test
	public void testGetCreateTableStatementWithoutForeignKeys() {

		String expectedStatement = new StringBuilder()
			.append("CREATE TABLE `database`.`table` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT + "),")
			.append("PRIMARY KEY (`id`));")
			.toString();

		DbTableStructure structure = new TestTableStructure();
		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setAutoIncrement(AUTO_INCREMENT)
			.setSkipForeignKeyCreation(true)
			.getCreateTableStatement();

		assertEquals(expectedStatement, actualStatement);
	}

	@Test
	public void testGetCreateTableStatementWithHumanReadableFormat() {

		String expectedStatement = new StringBuilder()
			.append("CREATE TABLE `database`.`table` (\n")
			.append("\t`id` INT NOT NULL AUTO_INCREMENT,\n")
			.append("\tPRIMARY KEY (`id`)\n")
			.append(");")
			.toString();

		DbTableStructure structure = new TestTableStructure();
		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setHumanReadable(true)
			.getCreateTableStatement();

		assertEquals(expectedStatement, actualStatement);
	}

	@Test
	public void testGetCreateTableStatementWithComment() {

		String expectedStatement = new StringBuilder()
			.append("CREATE TABLE `database`.`table` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT + "),")
			.append("PRIMARY KEY (`id`)) COMMENT='someComment';")
			.toString();

		DbTableStructure structure = new TestTableStructure();
		structure.setComment("someComment");

		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setAutoIncrement(AUTO_INCREMENT)
			.setSkipForeignKeyCreation(true)
			.getCreateTableStatement();

		assertEquals(expectedStatement, actualStatement);
	}

	@Test
	public void testGetCreateTableStatementWithSingleQuoteInComment() {

		String expectedStatement = new StringBuilder()
			.append("CREATE TABLE `database`.`table` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT + "),")
			.append("PRIMARY KEY (`id`)) COMMENT='some''quote';")
			.toString();

		DbTableStructure structure = new TestTableStructure();
		structure.setComment("some'quote");

		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setAutoIncrement(AUTO_INCREMENT)
			.setSkipForeignKeyCreation(true)
			.getCreateTableStatement();

		assertEquals(expectedStatement, actualStatement);
	}

	@Test
	public void testGetCreateTableStatementWithComplexTestObjectTable() {

		String expectedStatement = new DbComplexTestObjectTableLiteral()//
			.setIsH2Databse(true)
			.getCreateTableStatement();

		TestTableStructure structure = new TestTableStructureWithComplexProperties();
		String actualStatement = new DbTableStructureSqlGenerator(structure)//
			.setAutoIncrement(AUTO_INCREMENT)
			.setIsH2Database(true)
			.getCreateTableStatement();

		assertEquals(//
			String.format("Expected and actual statement differed.\n\nExpected:\n%s\n\nActual:\n%s\n\n", expectedStatement, actualStatement),
			expectedStatement,
			actualStatement);
	}

	private static class TestTableStructure extends DbTableStructure {

		public TestTableStructure() {

			super(new DbTableName("database", "table"));

			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("id")
					.setFieldType(SqlFieldType.INTEGER)
					.setBits(32)
					.setAutoIncrement(true));

			addKeyStructure(
				new DbKeyStructure(this)//
					.setType(DbKeyType.PRIMARY)
					.addColumnName("id"));
		}
	}

	private static class TestTableStructureWithForeignKeys extends TestTableStructure {

		public TestTableStructureWithForeignKeys() {

			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("fk")
					.setFieldType(SqlFieldType.INTEGER)
					.setBits(32)
					.setNullable(true));

			addForeignKeyStructure(
				new DbForeignKeyStructure(this)//
					.setName("fk_constraint")
					.addColumnPair("fk", "id")
					.setForeignTableName(new DbTableName("database", "table"))
					.setOnUpdateAction(DbForeignKeyAction.CASCADE));
		}
	}

	private static class TestTableStructureWithComplexProperties extends TestTableStructure {

		public TestTableStructureWithComplexProperties() {

			// ---- columns ---- //

			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("fk")
					.setFieldType(SqlFieldType.INTEGER)
					.setBits(32)
					.setNullable(true));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("long")
					.setFieldType(SqlFieldType.LONG)
					.setNullable(true));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("string")
					.setFieldType(SqlFieldType.STRING)
					.setMaxLength(255)
					.setDefaultType(DbColumnDefaultType.NORMAL)
					.setDefaultValue("abc")
					.setComment("someComment")
					.setNullable(true));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("stringWithCharacterSet")
					.setFieldType(SqlFieldType.STRING)
					.setMaxLength(128)
					.setCharacterSet("someCharacterSet")
					.setCollation("someCollation")
					.setNullable(false));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("enum")
					.setFieldType(SqlFieldType.ENUM)
					.setEnumValues(Arrays.asList("BIG", "MEDIUM", "SMALL", "TINY"))
					.setCharacterSet("someCharacterSet")
					.setNullable(false));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("unsignedInteger")
					.setFieldType(SqlFieldType.INTEGER)
					.setBits(32)
					.setUnsigned(true)
					.setDefaultType(DbColumnDefaultType.NULL)
					.setNullable(true));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("dayTime")
					.setFieldType(SqlFieldType.DAY_TIME)
					.setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP)
					.setOnUpdateNow(true)
					.setNullable(true));
			addColumnStructure(
				new DbColumnStructure(this)//
					.setName("timestamp")
					.setFieldType(SqlFieldType.DAY_TIME)
					.setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP)
					.setOnUpdateNow(true)
					.setTimestamp(true)
					.setNullable(true));

			// ---- keys ---- //

			addKeyStructure(
				new DbKeyStructure(this)//
					.setName("uniqueStringLong")
					.setType(DbKeyType.UNIQUE)
					.addColumnName("string")
					.addColumnName("long"));

			addKeyStructure(
				new DbKeyStructure(this)//
					.setName("long")
					.setType(DbKeyType.INDEX)
					.addColumnName("long"));

			// ---- foreign keys ---- //

			addForeignKeyStructure(
				new DbForeignKeyStructure(this)//
					.setName("fk_constraint")
					.addColumnPair("fk", "id")
					.setForeignTableName(new DbTableName("database", "table"))
					.setOnUpdateAction(DbForeignKeyAction.CASCADE));
		}
	}
}
