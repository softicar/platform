package com.softicar.platform.emf.test.simple.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.collection.set.AbstractEmfEntitySetTable;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestObjectSetTable extends AbstractEmfEntitySetTable<EmfTestObjectSet, EmfTestObjectSetItem, EmfTestObject, Void> {

	public EmfTestObjectSetTable(IDbObjectTableBuilder<EmfTestObjectSet> builder) {

		super(builder, EmfTestObjectSet.HASH);
	}

	@Override
	protected IDbRecordTable<EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> getItemTable() {

		return EmfTestObjectSetItem.TABLE;
	}

	@Override
	protected IDbForeignField<EmfTestObjectSetItem, EmfTestObjectSet> getItemSetField() {

		return EmfTestObjectSetItem.OBJECT_SET;
	}

	@Override
	protected IDbForeignRowField<EmfTestObjectSetItem, EmfTestObject, ?> getItemElementField() {

		return EmfTestObjectSetItem.OBJECT;
	}

	@Override
	public IEmfObjectTable<EmfTestObject, ?> getElementTable() {

		return EmfTestObject.TABLE;
	}
}
