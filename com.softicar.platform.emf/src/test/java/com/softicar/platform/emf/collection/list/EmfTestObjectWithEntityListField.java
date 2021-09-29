package com.softicar.platform.emf.collection.list;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.test.simple.list.EmfTestObjectList;
import java.util.Optional;

public class EmfTestObjectWithEntityListField extends AbstractDbObject<EmfTestObjectWithEntityListField>
		implements IEmfObject<EmfTestObjectWithEntityListField> {

	public static final DbObjectTableBuilder<EmfTestObjectWithEntityListField, EmfTestObjectWithEntityListField> BUILDER = new DbObjectTableBuilder<>(//
		"Test",
		EmfTestObjectWithEntityListField.class.getSimpleName(),
		EmfTestObjectWithEntityListField::new,
		EmfTestObjectWithEntityListField.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test List"));
		BUILDER.setTitle(IDisplayString.create("Test Lists"));
	}
	public static final IDbIdField<EmfTestObjectWithEntityListField> ID =//
			BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestObjectWithEntityListField, EmfTestObjectList> LIST =//
			BUILDER.addForeignField("list", o -> o.list, (o, v) -> o.list = v, EmfTestObjectList.ID);
	public static final EmfTestObjectWithEntityListFieldTable TABLE = new EmfTestObjectWithEntityListFieldTable(BUILDER);

	private Integer id;
	private EmfTestObjectList list;

	public EmfTestObjectList getList() {

		return getValue(LIST);
	}

	public EmfTestObjectWithEntityListField setList(EmfTestObjectList set) {

		return setValue(LIST, set);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return Optional//
			.ofNullable(getList())
			.map(IEmfEntityList::toDisplayWithoutId)
			.orElse(IDisplayString.EMPTY);
	}

	@Override
	public EmfTestObjectWithEntityListFieldTable table() {

		return TABLE;
	}
}
