package com.softicar.platform.db.runtime.table.row.getter;

import com.softicar.platform.db.runtime.cache.IDbTableRowCache;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class DbTableRowGetterLoadAllStrategyTest extends AbstractDbTableRowGetterTest {

	private final DbTableRowGetterLoadAllStrategy<DbTestObject, Integer> strategy;

	public DbTableRowGetterLoadAllStrategyTest() {

		this.strategy = new DbTableRowGetterLoadAllStrategy<>(DbTestObject.TABLE);
	}

	@Test
	public void testLoadRowsByPrimaryKey() {

		Map<Integer, DbTestObject> map = strategy.loadRowsByPrimaryKey(Collections.singleton(A));

		// assert that only the requested row was returned
		assertMap(map, A);

		// assert that all other rows are cached
		IDbTableRowCache<DbTestObject, Integer> cache = DbTestObject.TABLE.getCache();
		assertCachedObject(cache, A);
		assertCachedObject(cache, B);
		assertCachedObject(cache, C);
		assertCachedObject(cache, D);
	}

	private void assertCachedObject(IDbTableRowCache<DbTestObject, Integer> cache, int id) {

		DbTestObject object = cache.getSimple(id);
		assertNotNull(object);
		assertFalse(object.stub());
	}
}
