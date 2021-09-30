package com.softicar.platform.emf.collection.set;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.test.simple.set.EmfTestObjectSet;
import java.util.Optional;

public class EmfTestObjectWithEntitySetField extends AbstractDbObject<EmfTestObjectWithEntitySetField> implements IEmfObject<EmfTestObjectWithEntitySetField> {

	public static final DbObjectTableBuilder<EmfTestObjectWithEntitySetField, EmfTestObjectWithEntitySetField> BUILDER = new DbObjectTableBuilder<>(//
		"Test",
		EmfTestObjectWithEntitySetField.class.getSimpleName(),
		EmfTestObjectWithEntitySetField::new,
		EmfTestObjectWithEntitySetField.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Set"));
		BUILDER.setTitle(IDisplayString.create("Test Sets"));
	}
	public static final IDbIdField<EmfTestObjectWithEntitySetField> ID =//
			BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestObjectWithEntitySetField, EmfTestObjectSet> SET =//
			BUILDER.addForeignField("set", o -> o.set, (o, v) -> o.set = v, EmfTestObjectSet.ID);
	public static final EmfTestObjectWithEntitySetFieldTable TABLE = new EmfTestObjectWithEntitySetFieldTable(BUILDER);

	private Integer id;
	private EmfTestObjectSet set;

	public EmfTestObjectSet getSet() {

		return getValue(SET);
	}

	public EmfTestObjectWithEntitySetField setSet(EmfTestObjectSet set) {

		return setValue(SET, set);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return Optional//
			.ofNullable(getSet())
			.map(IEmfEntitySet::toDisplayWithoutId)
			.orElse(IDisplayString.EMPTY);
	}

	@Override
	public EmfTestObjectWithEntitySetFieldTable table() {

		return TABLE;
	}
}
