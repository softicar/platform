package com.softicar.platform.db.structure.view;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import org.junit.Test;

public class DbAliasViewTableStructureTest extends AbstractTest {

	private static final DbTableName VIEW_NAME = new DbTableName("db", "view");

	@Test
	public void test() {

		String tableDefinition = new StringBuilder()//
			.append("CREATE TABLE `db`.`tab` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT,")
			.append("`name` VARCHAR(255),")
			.append("`ref` INT,")
			.append("PRIMARY KEY(`id`),")
			.append("UNIQUE KEY `uk` (`ref`, `name`),")
			.append("CONSTRAINT `fk` FOREIGN KEY (`ref`) REFERENCES `db`.`other` (`id`) ON DELETE CASCADE ON UPDATE SET NULL")
			.append(");")
			.toString();
		String viewDefinition = "SELECT id AS idV, name AS nameV, ref AS refV FROM db.tab";

		DbAliasViewTableStructure aliasViewTableStructure = createAliasViewTableStructure(tableDefinition, viewDefinition);

		assertEquals(
			new StringBuilder()//
				.append("CREATE TABLE `db`.`view` (")
				.append("`idV` INT NOT NULL AUTO_INCREMENT,")
				.append("`nameV` VARCHAR(255),")
				.append("`refV` INT,")
				.append("PRIMARY KEY (`idV`),")
				.append("UNIQUE KEY `uk` (`refV`, `nameV`),")
				.append("CONSTRAINT `fk` FOREIGN KEY (`refV`) REFERENCES `db`.`other` (`id`) ON DELETE CASCADE ON UPDATE SET NULL")
				.append(");")
				.toString(),
			new DbTableStructureSqlGenerator(aliasViewTableStructure).getCreateTableStatement());
	}

	@Test
	public void testWithInvalidColumnName() {

		String tableDefinition = new StringBuilder()//
			.append("CREATE TABLE `db`.`tab` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT,")
			.append("`name` VARCHAR(255),")
			.append("PRIMARY KEY(`id`)")
			.append(");")
			.toString();
		String viewDefinition = "SELECT id AS id, invalid AS name FROM db.tab";

		DbAliasViewTableStructure aliasViewTableStructure = createAliasViewTableStructure(tableDefinition, viewDefinition);

		assertEquals(
			new StringBuilder()//
				.append("CREATE TABLE `db`.`view` (")
				.append("`id` INT NOT NULL AUTO_INCREMENT,")
				.append("PRIMARY KEY (`id`)")
				.append(");")
				.toString(),
			new DbTableStructureSqlGenerator(aliasViewTableStructure).getCreateTableStatement());
	}

	private DbAliasViewTableStructure createAliasViewTableStructure(String tableDefinition, String viewDefinition) {

		DbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(tableDefinition).parse();
		DbViewStructure viewStructure = new DbViewStructure(VIEW_NAME, viewDefinition);
		DbAliasView aliasView = new DbAliasViewParser(viewStructure).parse();
		return new DbAliasViewTableStructure(aliasView, tableStructure);
	}
}
