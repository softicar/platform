package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.object.DbAutomaticTestObjectMaster;
import com.softicar.platform.db.runtime.object.DbAutomaticTestObjectReference;
import com.softicar.platform.db.runtime.object.DbAutomaticTestObjectReferenceToMaster;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTinyTestObject;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.utils.AbstractDbTableStructureTest;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import java.util.Arrays;
import org.junit.Test;

public class DbTableStructureCreatorTest extends AbstractDbTableStructureTest {

	@Test
	public void testCreateTableWithForeignKeys() {

		new DbTableStructureCreator(DbTestObject.TABLE).create();
		assertForeignKeyMetaData(DbTestObject.TABLE, true);
	}

	@Test
	public void testCreateTableWithoutForeignKeys() {

		new DbTableStructureCreator(DbTestObject.TABLE).setSkipForeignKeyCreation(true).create();
		assertForeignKeyMetaData(DbTestObject.TABLE, false);
	}

	@Test
	public void testAutoIncrementValue() {

		setAutoIncrementSupplier(() -> 123);

		DbTestObject first = new DbTestObject().save();
		DbTestObject second = new DbTestObject().save();

		assertEquals(123, first.getId());
		assertEquals(124, second.getId());
	}

	@Test
	public void testCreateTablesWithLoopingForeignKeys() {

		DbAutomaticTestObjectMaster.TABLE.createTable();
		assertForeignKeyMetaDataPresent(DbAutomaticTestObjectMaster.TABLE);
		assertForeignKeyMetaDataPresent(DbAutomaticTestObjectReference.TABLE);
		assertForeignKeyMetaDataPresent(DbAutomaticTestObjectReferenceToMaster.TABLE);
	}

	@Test
	public void testCreateTableWithTwoTablesInSameStatement() {

		DbRuntimeTableStructure testObjectTableStructure = new DbRuntimeTableStructure(DbTestObject.TABLE);
		DbRuntimeTableStructure tinyTestObjectTableStructure = new DbRuntimeTableStructure(DbTinyTestObject.TABLE);

		String firstStatement = new DbTableStructureSqlGenerator(testObjectTableStructure).getCreateTableStatement();
		String secondStatement = new DbTableStructureSqlGenerator(tinyTestObjectTableStructure).getCreateTableStatement();

		new DbStatement()//
			.addText("CREATE SCHEMA IF NOT EXISTS `database`;")
			.execute();
		new DbStatement()//
			.addText(firstStatement + "\n" + secondStatement)
			.addTables(Arrays.asList(DbTestObject.TABLE, DbTinyTestObject.TABLE))
			.execute();
	}
}
