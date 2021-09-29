package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.List;
import org.junit.Assert;

public class DbAssertUtils {

	public static <T> List<T> assertCount(int expectedCount, IDbTable<T, ?> table) {

		List<T> all = table.loadAll();
		Assert.assertEquals(expectedCount, all.size());
		return all;
	}

	public static <T> T assertOne(IDbTable<T, ?> table) {

		return assertCount(1, table).get(0);
	}

	public static <T> void assertNone(IDbTable<T, ?> table) {

		assertCount(0, table);
	}
}
