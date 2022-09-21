package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;

public class DbTinyTestObject extends AbstractDbObject<DbTinyTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbTinyTestObject, DbTinyTestObject> BUILDER = new DbObjectTableBuilder<>("database", "tiny", DbTinyTestObject::new,DbTinyTestObject.class);
	public static final IDbIdField<DbTinyTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbStringField<DbTinyTestObject> STRING_FIELD = BUILDER.addStringField("string", o->o.string, (o,v)->o.string=v).setNullable().setMaximumLength(255);
	public static final IDbForeignField<DbTinyTestObject, DbTestObject> FOREIGN_FIELD = BUILDER.addForeignField("foreign", o->o.foreign, (o,v)->o.foreign=v, DbTestObject.ID_FIELD).setNullable();
	public static final DbObjectTable<DbTinyTestObject> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private String string;
	private DbTestObject foreign;

	public DbTinyTestObject() {

		// nothing to do
	}

	public DbTinyTestObject(Integer id) {

		this.id = id;
		this.string = null;
	}

	public String getString() {

		return STRING_FIELD.getValue(this);
	}

	public boolean setString(String value) {

		return STRING_FIELD.setValue(this, value);
	}

	@Override
	public DbObjectTable<DbTinyTestObject> table() {

		return TABLE;
	}
}
