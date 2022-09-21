package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public abstract class AbstractDbRecordTest extends AbstractDbTest {

	protected void insertRecord(Tuple2<String, Integer> key, String address) {

		insertRecord(key.get0(), key.get1(), address);
	}

	protected void insertRecord(String name, int index, String address) {

		DbTestRecord.TABLE//
			.createInsert()
			.set(DbTestRecord.NAME, name)
			.set(DbTestRecord.INDEX, index)
			.set(DbTestRecord.ADDRESS, address)
			.executeWithoutIdGeneration();
	}

	protected void insertTinyRecord(Integer key, Integer value) {

		DbTinyTestRecord.TABLE//
			.createInsert()
			.set(DbTinyTestRecord.KEY, key)
			.set(DbTinyTestRecord.VALUE, value)
			.executeWithoutIdGeneration();
	}
}
