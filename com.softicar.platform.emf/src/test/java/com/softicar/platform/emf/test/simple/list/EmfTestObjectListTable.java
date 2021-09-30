package com.softicar.platform.emf.test.simple.list;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.collection.list.AbstractEmfEntityListTable;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Optional;

public class EmfTestObjectListTable extends AbstractEmfEntityListTable<EmfTestObjectList, EmfTestObjectListItem, EmfTestObject, Void> {

	public EmfTestObjectListTable(IDbObjectTableBuilder<EmfTestObjectList> builder) {

		super(builder, EmfTestObjectList.HASH);
	}

	@Override
	protected IDbRecordTable<EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> getItemTable() {

		return EmfTestObjectListItem.TABLE;
	}

	@Override
	protected IDbForeignField<EmfTestObjectListItem, EmfTestObjectList> getItemSetField() {

		return EmfTestObjectListItem.OBJECT_LIST;
	}

	@Override
	protected IDbForeignRowField<EmfTestObjectListItem, EmfTestObject, ?> getItemElementField() {

		return EmfTestObjectListItem.OBJECT;
	}

	@Override
	protected Optional<IDbIntegerField<EmfTestObjectListItem>> getItemIndexField() {

		return Optional.of(EmfTestObjectListItem.INDEX);
	}

	@Override
	public IEmfObjectTable<EmfTestObject, ?> getElementTable() {

		return EmfTestObject.TABLE;
	}
}
