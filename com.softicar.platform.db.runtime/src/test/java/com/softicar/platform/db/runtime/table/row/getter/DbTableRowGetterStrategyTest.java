package com.softicar.platform.db.runtime.table.row.getter;

import com.softicar.platform.db.runtime.cache.IDbTableRowCache;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class DbTableRowGetterStrategyTest extends AbstractDbTableRowGetterTest {

	private final DbTableRowGetterStrategy<DbTestObject, Integer> strategy;

	public DbTableRowGetterStrategyTest() {

		this.strategy = new DbTableRowGetterStrategy<>(DbTestObject.TABLE);
	}

	@Test
	public void testLoadRowsByPrimaryKey() {

		Map<Integer, DbTestObject> map = strategy.loadRowsByPrimaryKey(Collections.singleton(A));

		// assert that only the requested row was returned
		assertMap(map, A);

		// assert that only the requested row is cached
		IDbTableRowCache<DbTestObject, Integer> cache = DbTestObject.TABLE.getCache();
		assertNotNull(cache.getSimple(A));
		assertNull(cache.getSimple(B));
		assertNull(cache.getSimple(C));
		assertNull(cache.getSimple(D));
	}
}
