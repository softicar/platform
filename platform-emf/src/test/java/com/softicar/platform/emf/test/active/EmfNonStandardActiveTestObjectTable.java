package com.softicar.platform.emf.test.active;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfNonStandardActiveTestObjectTable extends EmfObjectTable<EmfNonStandardActiveTestObject, EmfTestModuleInstance> {

	public EmfNonStandardActiveTestObjectTable(IDbObjectTableBuilder<EmfNonStandardActiveTestObject> builder) {

		super(builder);
	}
}
