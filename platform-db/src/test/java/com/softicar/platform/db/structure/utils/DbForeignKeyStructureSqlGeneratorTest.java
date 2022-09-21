package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import org.junit.Test;

public class DbForeignKeyStructureSqlGeneratorTest extends AbstractTest {

	@Test
	public void test() {

		IDbTableStructure tableStructure = new DbTableStructure(new DbTableName("db", "tab"));

		IDbForeignKeyStructure foreignKeyStructure = new DbForeignKeyStructure(tableStructure)//
			.setName("con")
			.setForeignTableName(new DbTableName("db", "foreign"))
			.addColumnPair("a", "aa")
			.addColumnPair("b", "bb")
			.setOnDeleteAction(DbForeignKeyAction.CASCADE)
			.setOnUpdateAction(DbForeignKeyAction.NO_ACTION);

		String string = new DbForeignKeyStructureSqlGenerator(foreignKeyStructure).toString();

		assertEquals("CONSTRAINT `con` FOREIGN KEY (`a`,`b`) REFERENCES `db`.`foreign` (`aa`,`bb`) ON DELETE CASCADE ON UPDATE NO ACTION", string);
	}
}
