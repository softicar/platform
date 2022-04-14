package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.utils.DbComplexTestObject;
import org.junit.Test;

public class DbRuntimeTableStructureTest extends AbstractTest {

	private final DbRuntimeTableStructure tableStructure;

	public DbRuntimeTableStructureTest() {

		this.tableStructure = new DbRuntimeTableStructure(DbComplexTestObject.TABLE);
	}

	@Test
	public void testTableName() {

		assertEquals("`database`.`table`", tableStructure.getTableName().getQuoted());
	}

	@Test
	public void testColumns() {

		StringBuilder columns = new StringBuilder()//
			.append("[")
			.append("`id` INT NOT NULL AUTO_INCREMENT, ")
			.append("`fk` INT, ")
			.append("`fk2` INT NOT NULL, ")
			.append("`long` BIGINT, ")
			.append("`string` VARCHAR(255) DEFAULT 'abc' COMMENT 'someComment', ")
			.append("`stringWithCharacterSetAndCollation` VARCHAR(128) CHARACTER SET someCharacterSet COLLATE someCollation NOT NULL, ")
			.append("`enum` ENUM('BIG','MEDIUM','SMALL','TINY') CHARACTER SET someCharacterSet NOT NULL, ")
			.append("`unsignedInteger` INT UNSIGNED DEFAULT NULL, ")
			.append("`dayTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
			.append("]");

		assertEquals(columns.toString(), tableStructure.getColumns().toString());
	}

	@Test
	public void testKeys() {

		assertEquals(
			"[PRIMARY KEY (`id`), UNIQUE KEY `uk$table$string$long` (`string`, `long`), KEY `ik$table$long` (`long`)]",//
			tableStructure.getKeys().toString());
	}

	@Test
	public void testForeginKeys() {

		assertEquals(2, tableStructure.getForeignKeys().size());
		assertEquals(
			"CONSTRAINT `fk_constraint` FOREIGN KEY (`fk`) REFERENCES `database`.`table` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE",//
			tableStructure.getForeignKeys().get(0).toString());
		assertEquals(
			"FOREIGN KEY (`fk2`) REFERENCES `database`.`table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT",//
			tableStructure.getForeignKeys().get(1).toString());
	}

	@Test
	public void testWithEnumTable() {

		DbRuntimeTableStructure enumTableStructure = new DbRuntimeTableStructure(InternalEnumTestTableRow.TABLE);

		assertTrue(enumTableStructure.isEnumTable());
		assertFalse(tableStructure.isEnumTable());
		assertEquals("[{id=1, name=foo}, {id=2, name=bar}, {id=3, name=baz}]", enumTableStructure.getEnumTableRows().toString());
	}
}
