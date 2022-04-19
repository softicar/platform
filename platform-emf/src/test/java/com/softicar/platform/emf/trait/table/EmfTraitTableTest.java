package com.softicar.platform.emf.trait.table;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.trait.EmfTestTrait;
import java.util.Optional;
import org.junit.Test;

public class EmfTraitTableTest extends AbstractTest {

	@Test
	public void testGetScopeField() {

		Optional<ISqlForeignRowField<EmfTestTrait, EmfTestObject, ?>> scopeField = EmfTestTrait.TABLE.getScopeField();

		assertTrue(scopeField.isPresent());
		assertSame(EmfTestTrait.OBJECT, scopeField.get());
	}

	@Test
	public void testGetTargetTable() {

		IEmfTable<EmfTestObject, ?, ?> targetTable = EmfTestTrait.TABLE.getTargetTable();

		assertSame(EmfTestObject.TABLE, targetTable);
	}
}
