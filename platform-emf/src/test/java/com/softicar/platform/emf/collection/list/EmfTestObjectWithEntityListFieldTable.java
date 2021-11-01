package com.softicar.platform.emf.collection.list;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class EmfTestObjectWithEntityListFieldTable extends EmfObjectTable<EmfTestObjectWithEntityListField, Void> {

	public EmfTestObjectWithEntityListFieldTable(IDbObjectTableBuilder<EmfTestObjectWithEntityListField> builder) {

		super(builder);
	}
}
