package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.table.row.getter.AbstractDbTableRowGetterTest;
import java.util.Arrays;
import java.util.Map;
import org.junit.Test;

public class DbTableRowGetterTest extends AbstractDbTableRowGetterTest {

	@Test
	public void testGetAsMap() {

		Map<Integer, DbTestObject> map1 = new DbTableRowGetter<>(DbTestObject.TABLE, Arrays.asList(A, B, C)).getAsMap();
		assertMap(map1, A, B, C);

		Map<Integer, DbTestObject> map2 = new DbTableRowGetter<>(DbTestObject.TABLE, Arrays.asList(A, B, C)).getAsMap();
		assertMap(map2, A, B, C);

		// assert that only one SELECT was executed
		assertExecutedStatements(1);
	}

	@Test
	public void testGetAsMapWithInvalidatedRow() {

		Map<Integer, DbTestObject> map1 = new DbTableRowGetter<>(DbTestObject.TABLE, Arrays.asList(A, B, C)).getAsMap();
		assertMap(map1, A, B, C);

		map1.get(B).invalidate();

		Map<Integer, DbTestObject> map2 = new DbTableRowGetter<>(DbTestObject.TABLE, Arrays.asList(A, B, C)).getAsMap();
		assertMap(map2, A, B, C);

		// assert that two SELECTs were executed and object B is valid again
		assertExecutedStatements(2);
		assertFalse(map1.get(B).invalidated());
	}

	@Test
	public void testGetAsMapWithStub() {

		DbTestObject object = DbTestObject.TABLE.getStub(B);

		new DbTableRowGetter<>(DbTestObject.TABLE, Arrays.asList(A, B, C)).getAsMap();

		// assert one SELECT was executed and object B was un-stubbed
		assertExecutedStatements(1);
		assertFalse(object.stub());
	}
}
