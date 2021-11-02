package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class DbTestBaseObject extends AbstractDbObject<DbTestBaseObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbTestBaseObject, DbTestBaseObject> BUILDER = new DbObjectTableBuilder<>("db", "Base", DbTestBaseObject::new, DbTestBaseObject.class);
	public static final IDbIdField<DbTestBaseObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<DbTestBaseObject> BASE_TEXT = BUILDER.addStringField("text", o -> o.baseText, (o, v) -> o.baseText= v).setDefault("");
	public static final DbObjectTable<DbTestBaseObject> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private String baseText;

	@Override
	public IDbObjectTable<DbTestBaseObject> table() {

		return TABLE;
	}

	public DbTestBaseObject setBaseText(String title) {

		return setValue(BASE_TEXT, title);
	}

	public String getBaseText() {

		return getValue(BASE_TEXT);
	}
}
