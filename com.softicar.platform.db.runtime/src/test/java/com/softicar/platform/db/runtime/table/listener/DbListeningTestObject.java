package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class DbListeningTestObject extends AbstractDbObject<DbListeningTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbListeningTestObject, DbListeningTestObject> BUILDER = new DbObjectTableBuilder<>("db", "listening", DbListeningTestObject::new, DbListeningTestObject.class);
	public static final IDbIdField<DbListeningTestObject> ID_FIELD = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<DbListeningTestObject> STRING_FIELD = BUILDER.addStringField("string", o -> o.string, (o, v) -> o.string = v).setDefault("");
	public static final DbListeningTestObjectTable TABLE = new DbListeningTestObjectTable();
	// @formatter:on

	private Integer id;
	private String string;

	@Override
	public IDbObjectTable<DbListeningTestObject> table() {

		return TABLE;
	}

	public DbListeningTestObject setString(String string) {

		return setValue(STRING_FIELD, string);
	}

	public String getString() {

		return getValue(STRING_FIELD);
	}
}
