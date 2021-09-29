package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;

public class DbIdGeneratorTestObject extends AbstractDbObject<DbIdGeneratorTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbIdGeneratorTestObject, DbIdGeneratorTestObject> BUILDER = new DbObjectTableBuilder<>("database", "tiny", DbIdGeneratorTestObject::new,DbIdGeneratorTestObject.class);
	public static final IDbIdField<DbIdGeneratorTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final DbObjectTable<DbIdGeneratorTestObject> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;

	public DbIdGeneratorTestObject() {

		// nothing to do
	}

	public DbIdGeneratorTestObject(Integer id) {

		this.id = id;
	}

	@Override
	public DbObjectTable<DbIdGeneratorTestObject> table() {

		return TABLE;
	}
}
