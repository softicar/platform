package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbEnumField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbLongField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.DbTestObjectFlag;
import com.softicar.platform.db.structure.test.DbComplexTestObjectTableLiteral;

/**
 * Corresponds to {@link DbComplexTestObjectTableLiteral}.
 */
public class DbComplexTestObject extends AbstractDbObject<DbComplexTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbComplexTestObject, DbComplexTestObject> BUILDER = new DbObjectTableBuilder<>("database", "table", DbComplexTestObject::new, DbComplexTestObject.class);
	public static final IDbIdField<DbComplexTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignField<DbComplexTestObject, DbComplexTestObject> FOREIGN_FIELD = BUILDER.addForeignField("fk", o->o.foreign, (o,v)->o.foreign=v, DbComplexTestObject.ID_FIELD).setNullable().setCascade(false, true).setForeignKeyName("fk_constraint");
	public static final IDbForeignField<DbComplexTestObject, DbComplexTestObject> FOREIGN_FIELD2 = BUILDER.addForeignField("fk2", o->o.foreign2, (o,v)->o.foreign2=v, DbComplexTestObject.ID_FIELD);
	public static final IDbLongField<DbComplexTestObject> LONG_FIELD = BUILDER.addLongField("long", o->o.longValue, (o,v)->o.longValue=v).setNullable();
	public static final IDbStringField<DbComplexTestObject> STRING_FIELD = BUILDER.addStringField("string", o->o.string, (o,v)->o.string=v).setDefault("abc").setNullable().setMaximumLength(255).setComment("someComment");
	public static final IDbStringField<DbComplexTestObject> STRING_WITH_CHARACTER_SET_AND_COLLATION_FIELD = BUILDER.addStringField("stringWithCharacterSetAndCollation", o->o.stringWithCharacterSetAndCollation, (o,v)->o.stringWithCharacterSetAndCollation=v).setMaximumLength(128).setCharacterSet("someCharacterSet").setCollation("someCollation");
	public static final IDbEnumField<DbComplexTestObject, DbTestObjectFlag> ENUM_FIELD = BUILDER.addEnumField("enum", o->o.enumField, (o,v)->o.enumField=v, DbTestObjectFlag.class).setCharacterSet("someCharacterSet");
	public static final IDbIntegerField<DbComplexTestObject> UNSIGNED_INTEGER_FIELD = BUILDER.addIntegerField("unsignedInteger", o->o.unsignedInteger, (o,v)->o.unsignedInteger=v).setNullable().setDefault(null).setUnsigned();
	public static final IDbDayTimeField<DbComplexTestObject> DAY_TIME_FIELD = BUILDER.addDayTimeField("dayTime", o->o.dayTime, (o,v)->o.dayTime=v).setNullable().setDefaultNow().setOnUpdateNow();
	public static final IDbKey<DbComplexTestObject> UK_STRING_FIELD = BUILDER.addUniqueKey("uk$table$string$long", STRING_FIELD, LONG_FIELD);
	public static final IDbKey<DbComplexTestObject> IK_LONG_FIELD = BUILDER.addIndexKey("ik$table$long", LONG_FIELD);
	public static final DbObjectTable<DbComplexTestObject> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private DbComplexTestObject foreign;
	private DbComplexTestObject foreign2;
	private Long longValue;
	private String string;
	private String stringWithCharacterSetAndCollation;
	private DbTestObjectFlag enumField;
	private Integer unsignedInteger;
	private DayTime dayTime;

	public DbComplexTestObject() {

		// nothing to do
	}

	public DbComplexTestObject(Integer id) {

		initializeFlags();
		this.id = id;
	}

	public DbComplexTestObject(DbComplexTestObject other) {

		initializer().initializeCopy(other);
	}

	public DbComplexTestObject getForeign() {

		return FOREIGN_FIELD.getValue(this);
	}

	public boolean setForeign(DbComplexTestObject value) {

		return FOREIGN_FIELD.setValue(this, value);
	}

	public DbComplexTestObject getForeign2() {

		return FOREIGN_FIELD2.getValue(this);
	}

	public boolean setForeign2(DbComplexTestObject value) {

		return FOREIGN_FIELD2.setValue(this, value);
	}

	public Long getLong() {

		return LONG_FIELD.getValue(this);
	}

	public boolean setLong(Long value) {

		return LONG_FIELD.setValue(this, value);
	}

	public String getString() {

		return STRING_FIELD.getValue(this);
	}

	public boolean setString(String value) {

		return STRING_FIELD.setValue(this, value);
	}

	public String getStringWithCharacterSetAndCollation() {

		return STRING_WITH_CHARACTER_SET_AND_COLLATION_FIELD.getValue(this);
	}

	public boolean setStringWithCharacterSetAndCollation(String value) {

		return STRING_WITH_CHARACTER_SET_AND_COLLATION_FIELD.setValue(this, value);
	}

	public DbTestObjectFlag getEnum() {

		return ENUM_FIELD.getValue(this);
	}

	public boolean setEnum(DbTestObjectFlag value) {

		return ENUM_FIELD.setValue(this, value);
	}

	public Integer getUnsignedInteger() {

		return UNSIGNED_INTEGER_FIELD.getValue(this);
	}

	public boolean setUnsignedInteger(Integer value) {

		return UNSIGNED_INTEGER_FIELD.setValue(this, value);
	}

	public DayTime getDayTime() {

		return DAY_TIME_FIELD.getValue(this);
	}

	public boolean setDayTime(DayTime value) {

		return DAY_TIME_FIELD.setValue(this, value);
	}

	@Override
	public DbObjectTable<DbComplexTestObject> table() {

		return TABLE;
	}
}
