package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.utils.AbstractDbTableStructureTest;
import org.junit.Test;

public class DbTableStructureForeignKeysAdderTest extends AbstractDbTableStructureTest {

	@Test
	public void testAddForeignKeys() {

		// create table without foreign keys
		new DbTableStructureCreator(DbTestObject.TABLE)//
			.setSkipForeignKeyCreation(true)
			.create();
		assertForeignKeyMetaData(DbTestObject.TABLE, false);

		// now add foreign keys
		new DbTableStructureForeignKeysAdder(DbTestObject.TABLE)//
			.addForeignKeys();
		assertForeignKeyMetaData(DbTestObject.TABLE, true);
	}
}
