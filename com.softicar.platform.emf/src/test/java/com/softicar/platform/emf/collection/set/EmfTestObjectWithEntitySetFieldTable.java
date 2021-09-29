package com.softicar.platform.emf.collection.set;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class EmfTestObjectWithEntitySetFieldTable extends EmfObjectTable<EmfTestObjectWithEntitySetField, Void> {

	public EmfTestObjectWithEntitySetFieldTable(IDbObjectTableBuilder<EmfTestObjectWithEntitySetField> builder) {

		super(builder);
	}
}
