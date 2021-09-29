package com.softicar.platform.emf.record;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.EmfTestScope;
import com.softicar.platform.emf.record.table.EmfRecordTable;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestRecordWithObjectPkTable extends EmfRecordTable<EmfTestRecordWithObjectPk, EmfTestObject, EmfTestScope> {

	public EmfTestRecordWithObjectPkTable(IDbTableBuilder<EmfTestRecordWithObjectPk, EmfTestObject> builder) {

		super(builder);
	}
}
