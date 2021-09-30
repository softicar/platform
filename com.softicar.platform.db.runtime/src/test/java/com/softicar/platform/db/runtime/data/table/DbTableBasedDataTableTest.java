package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.db.runtime.object.DbTestObject;
import org.junit.Assert;
import org.junit.Test;

public class DbTableBasedDataTableTest extends Assert {

	@Test
	public void testGetIdentifier() {

		DbTableBasedDataTable<DbTestObject> table = new DbTableBasedDataTable<>(DbTestObject.TABLE);
		DataTableIdentifier identifier = table.getIdentifier();

		assertEquals("database.table", identifier.toString());
	}
}
