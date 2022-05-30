package com.softicar.platform.emf.test.simple.set;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.entity.set.AbstractEmfEntitySetTable;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestObjectSetTable extends AbstractEmfEntitySetTable<EmfTestObjectSet, EmfTestObjectSetItem, EmfTestObject, Void> {

	public EmfTestObjectSetTable(IDbObjectTableBuilder<EmfTestObjectSet> builder) {

		super(//
			builder,
			EmfTestObjectSetItem.TABLE,
			EmfTestObjectSetItem.OBJECT_SET,
			EmfTestObjectSetItem.OBJECT,
			EmfTestObjectSet.HASH);
	}
}
