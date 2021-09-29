package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;

public class DbTestSubSubObject extends AbstractDbSubObject<DbTestSubSubObject, DbTestSubObject> {

	// @formatter:off
	public static final DbSubObjectTableBuilder<DbTestSubSubObject, DbTestSubSubObject, DbTestSubObject, DbTestBaseObject> BUILDER = new DbSubObjectTableBuilder<>("db", "SubSub", DbTestSubSubObject::new,DbTestSubSubObject.class);
	public static final IDbBaseField<DbTestSubSubObject, DbTestSubObject, ?> SUB_OBJECT = BUILDER.addBaseField("subObject", o -> o.subObject, (o, v) -> o.subObject = v, DbTestSubObject.TABLE).setOnDelete(DbForeignKeyAction.CASCADE);
	public static final IDbStringField<DbTestSubSubObject> SUB_SUB_TEXT = BUILDER.addStringField("subSubText", o -> o.subSubText, (o, v) -> o.subSubText= v).setDefault("");
	public static final DbSubObjectTable<DbTestSubSubObject, DbTestSubObject, DbTestBaseObject> TABLE = new DbSubObjectTable<>(BUILDER);
	// @formatter:on

	private DbTestSubObject subObject;
	private String subSubText;

	@Override
	public IDbSubObjectTable<DbTestSubSubObject, DbTestSubObject, DbTestBaseObject> table() {

		return TABLE;
	}

	public DbTestSubObject getSubObject() {

		return pk();
	}

	public DbTestSubSubObject setSubSubText(String requester) {

		return setValue(SUB_SUB_TEXT, requester);
	}

	public String getSubSubText() {

		return getValue(SUB_SUB_TEXT);
	}
}
