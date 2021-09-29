package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;

public class DbTestSubObject extends AbstractDbSubObject<DbTestSubObject, DbTestBaseObject> {

	// @formatter:off
	public static final DbSubObjectTableBuilder<DbTestSubObject, DbTestSubObject, DbTestBaseObject, Integer> BUILDER = new DbSubObjectTableBuilder<>("db", "Sub", DbTestSubObject::new,DbTestSubObject.class);
	public static final IDbForeignRowField<DbTestSubObject, DbTestBaseObject, Integer> BASE_OBJECT = BUILDER.addBaseField("baseObject", o -> o.baseObject, (o, v) -> o.baseObject = v, DbTestBaseObject.TABLE).setOnDelete(DbForeignKeyAction.CASCADE);
	public static final IDbStringField<DbTestSubObject> SUB_TEXT = BUILDER.addStringField("subText", o -> o.subText, (o, v) -> o.subText= v).setDefault("");
	public static final DbSubObjectTable<DbTestSubObject, DbTestBaseObject, Integer> TABLE = new DbSubObjectTable<>(BUILDER);
	// @formatter:on

	private DbTestBaseObject baseObject;
	private String subText;

	@Override
	public IDbSubObjectTable<DbTestSubObject, DbTestBaseObject, Integer> table() {

		return TABLE;
	}

	public DbTestBaseObject getBaseObject() {

		return pk();
	}

	public DbTestSubObject setSubText(String assignee) {

		return setValue(SUB_TEXT, assignee);
	}

	public String getSubText() {

		return getValue(SUB_TEXT);
	}
}
