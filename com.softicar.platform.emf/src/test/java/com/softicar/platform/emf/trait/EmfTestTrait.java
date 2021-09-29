package com.softicar.platform.emf.trait;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestTrait extends AbstractDbRecord<EmfTestTrait, EmfTestObject> implements IEmfTrait<EmfTestTrait, EmfTestObject> {

	private static final DbTableBuilder<EmfTestTrait, EmfTestTrait, EmfTestObject> BUILDER =//
			new DbTableBuilder<>("db", "trait", EmfTestTrait::new, EmfTestTrait.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Trait"));
		BUILDER.setTitle(IDisplayString.create("Test Traits"));
	}
	public static final IDbForeignField<EmfTestTrait, EmfTestObject> OBJECT =//
			BUILDER.addForeignField("object", r -> r.object, (r, v) -> r.object = v, EmfTestObject.ID);
	public static final IDbIntegerField<EmfTestTrait> VALUE =//
			BUILDER.addIntegerField("value", r -> r.value, (r, v) -> r.value = v);
	public static final IDbTableKey<EmfTestTrait, EmfTestObject> PRIMARY_KEY =//
			BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(OBJECT));
	public static final EmfTestTraitTable TABLE = new EmfTestTraitTable(BUILDER);

	private EmfTestObject object;
	private Integer value;

	@Override
	public EmfTestTraitTable table() {

		return TABLE;
	}

	public EmfTestObject getObject() {

		return getValue(OBJECT);
	}

	public final Integer getValue() {

		return getValue(VALUE);
	}

	public final EmfTestTrait setValue(Integer value) {

		return setValue(VALUE, value);
	}
}
