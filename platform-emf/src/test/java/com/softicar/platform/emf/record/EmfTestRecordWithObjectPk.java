package com.softicar.platform.emf.record;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestRecordWithObjectPk extends AbstractDbRecord<EmfTestRecordWithObjectPk, EmfTestObject>
		implements IEmfRecord<EmfTestRecordWithObjectPk, EmfTestObject> {

	private static final DbTableBuilder<EmfTestRecordWithObjectPk, EmfTestRecordWithObjectPk, EmfTestObject> BUILDER =
			new DbTableBuilder<>("db", "foo", EmfTestRecordWithObjectPk::new, EmfTestRecordWithObjectPk.class);
	public static final IDbForeignField<EmfTestRecordWithObjectPk, EmfTestObject> OBJECT =
			BUILDER.addForeignField("object", r -> r.object, (r, v) -> r.object = v, EmfTestObject.ID);
	public static final IDbIntegerField<EmfTestRecordWithObjectPk> VALUE = BUILDER.addIntegerField("value", r -> r.value, (r, v) -> r.value = v);
	public static final IDbTableKey<EmfTestRecordWithObjectPk, EmfTestObject> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(OBJECT));
	public static final EmfTestRecordWithObjectPkTable TABLE = new EmfTestRecordWithObjectPkTable(BUILDER);

	private EmfTestObject object;
	private Integer value;

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create("Record of ").concat(object.toDisplay());
	}

	@Override
	public EmfTestRecordWithObjectPkTable table() {

		return TABLE;
	}

	public EmfTestObject getObject() {

		return getValue(OBJECT);
	}

	public final Integer getValue() {

		return getValue(VALUE);
	}

	public final EmfTestRecordWithObjectPk setValue(Integer value) {

		return setValue(VALUE, value);
	}
}
