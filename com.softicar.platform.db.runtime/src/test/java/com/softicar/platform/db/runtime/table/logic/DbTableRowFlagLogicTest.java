package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DbTableRowFlagLogicTest extends Assert {

	@Test
	public void testFlagArraySize() {

		assertEquals(5, DbTableRowFlag.values().length);
		assertEquals(1, DbTableRowFlagsFactory.createFlags(createTableWithDataFields(0)).length);
		assertEquals(1, DbTableRowFlagsFactory.createFlags(createTableWithDataFields(3)).length);
		assertEquals(2, DbTableRowFlagsFactory.createFlags(createTableWithDataFields(4)).length);
		assertEquals(2, DbTableRowFlagsFactory.createFlags(createTableWithDataFields(11)).length);
		assertEquals(3, DbTableRowFlagsFactory.createFlags(createTableWithDataFields(12)).length);
	}

	private IDbObjectTable<DbTestObject> createTableWithDataFields(int dataFieldCount) {

		IDbObjectTable<DbTestObject> table = CastUtils.cast(Mockito.mock(IDbObjectTable.class));
		List<IDbField<DbTestObject, ?>> fields = new ArrayList<>();
		for (int i = 0; i < dataFieldCount; ++i) {
			fields.add(null);
		}
		Mockito.when(table.getDataFields()).thenAnswer(mock -> fields);
		return table;
	}
}
