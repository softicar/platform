package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import org.junit.Test;

public class DbRecordCompareToTest extends AbstractDbRecordTest {

	@Test
	public void testCompareTo() {

		DbTestRecord first = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("A", 7));
		DbTestRecord second = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("A", 8));
		DbTestRecord third = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("B", 8));

		assertTrue(first.compareTo(first) == 0);
		assertTrue(first.compareTo(second) != 0);
		assertTrue(first.compareTo(third) != 0);

		assertTrue(second.compareTo(first) != 0);
		assertTrue(second.compareTo(second) == 0);
		assertTrue(second.compareTo(third) != 0);

		assertTrue(third.compareTo(first) != 0);
		assertTrue(third.compareTo(second) != 0);
		assertTrue(third.compareTo(third) == 0);
	}
}
