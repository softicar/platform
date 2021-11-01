package com.softicar.platform.db.structure.utils;

import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import org.junit.Assert;
import org.junit.Test;

public class DbAlterTableAddForeignKeysSqlGeneratorTest extends Assert {

	private final IDbTableStructure tableStructure;

	public DbAlterTableAddForeignKeysSqlGeneratorTest() {

		this.tableStructure = new DbMysqlCreateTableStatementParser(
			new StringBuilder()//
				.append("CREATE TABLE `db`.`foo` (")
				.append("`bar` INT,")
				.append("`baz` INT,")
				.append("CONSTRAINT `bar` FOREIGN KEY (`bar`) REFERENCES `db`.`bar` (`id`),")
				.append("CONSTRAINT `baz` FOREIGN KEY (`baz`) REFERENCES `db`.`baz` (`id`));")
				.toString()).parse();
	}

	@Test
	public void testGetAlterTableStatement() {

		String expectedStatement = new StringBuilder()//
			.append("ALTER TABLE `db`.`foo` ")
			.append("ADD CONSTRAINT `bar` FOREIGN KEY (`bar`) REFERENCES `db`.`bar` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,\n")
			.append("ADD CONSTRAINT `baz` FOREIGN KEY (`baz`) REFERENCES `db`.`baz` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT")
			.toString();
		DbAlterTableAddForeignKeysSqlGenerator generator = new DbAlterTableAddForeignKeysSqlGenerator(tableStructure).setIsH2Database(false);
		assertEquals(expectedStatement, generator.getAlterTableStatement());
	}

	@Test
	public void testGetAlterTableStatementWithH2() {

		String expectedStatement = new StringBuilder()//
			.append("ALTER TABLE `db`.`foo` ADD CONSTRAINT `bar` FOREIGN KEY (`bar`) REFERENCES `db`.`bar` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;\n")
			.append("ALTER TABLE `db`.`foo` ADD CONSTRAINT `baz` FOREIGN KEY (`baz`) REFERENCES `db`.`baz` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;\n")
			.toString();
		DbAlterTableAddForeignKeysSqlGenerator generator = new DbAlterTableAddForeignKeysSqlGenerator(tableStructure).setIsH2Database(true);
		assertEquals(expectedStatement, generator.getAlterTableStatement());
	}
}
