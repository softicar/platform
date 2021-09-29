package com.softicar.platform.emf.form.derived;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestObjectWithDerivedValueGenerated extends AbstractDbObject<EmfTestObjectWithDerivedValue> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestObjectWithDerivedValue, EmfTestObjectWithDerivedValueGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "ObjectWithDerivedValue", EmfTestObjectWithDerivedValue::new, EmfTestObjectWithDerivedValue.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object"));
		BUILDER.setTitle(IDisplayString.create("Test Objects"));
	}
	public static final IDbIdField<EmfTestObjectWithDerivedValue> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbIntegerField<EmfTestObjectWithDerivedValue> VALUE = BUILDER.addIntegerField("value", o -> o.value, (o, v) -> o.value = v).setNullable().setDefault(null);
	public static final IDbIntegerField<EmfTestObjectWithDerivedValue> DERIVED_VALUE = BUILDER.addIntegerField("derivedValue", o -> o.derivedValue, (o, v) -> o.derivedValue = v).setNullable().setDefault(null);
	public static final EmfTestObjectWithDerivedValueTable TABLE = new EmfTestObjectWithDerivedValueTable(BUILDER);
	// @formatter:on

	private Integer id;
	private Integer value;
	private Integer derivedValue;

	public Integer getValue() {

		return getValue(VALUE);
	}

	public EmfTestObjectWithDerivedValue setValue(Integer value) {

		return setValue(VALUE, value);
	}

	public Integer getDerivedValue() {

		return getValue(DERIVED_VALUE);
	}

	public EmfTestObjectWithDerivedValue setDerivedValue(Integer derivedValue) {

		return setValue(DERIVED_VALUE, derivedValue);
	}

	@Override
	public EmfTestObjectWithDerivedValueTable table() {

		return TABLE;
	}
}
