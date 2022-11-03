package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.db.runtime.field.IDbEnumField;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbLongField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import java.math.BigDecimal;

public class DbTestObject extends AbstractDbObject<DbTestObject> {

	public static final Integer DEFAULT_INTEGER_VALUE = 1234;
	public static final String DEFAULT_STRING_VALUE = "abc";

	// @formatter:off
	public static final DbObjectTableBuilder<DbTestObject, DbTestObject> BUILDER = new DbObjectTableBuilder<>("database", "table", DbTestObject::new, DbTestObject.class);
	public static final IDbIdField<DbTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbBigDecimalField<DbTestObject> BIG_DECIMAL_FIELD = BUILDER.addBigDecimalField("decimal", o->o.decimal, (o,v)->o.decimal=v).setSize(20, 10).setNullable();
	public static final IDbBooleanField<DbTestObject> BOOLEAN_FIELD = BUILDER.addBooleanField("boolean", o->o.booleanValue, (o,v)->o.booleanValue=v).setNullable();
	public static final IDbByteArrayField<DbTestObject> BYTES_FIELD = BUILDER.addByteArrayField("bytes", o->o.bytes, (o,v)->o.bytes=v).setNullable().setMaximumLength(255);
	public static final IDbDayField<DbTestObject> DAY_FIELD = BUILDER.addDayField("day", o->o.day, (o,v)->o.day=v).setNullable();
	public static final IDbDayTimeField<DbTestObject> DAY_TIME_FIELD = BUILDER.addDayTimeField("dayTime", o->o.dayTime, (o,v)->o.dayTime=v).setNullable();
	public static final IDbDoubleField<DbTestObject> DOUBLE_FIELD = BUILDER.addDoubleField("double", o->o.doubleValue, (o,v)->o.doubleValue=v).setNullable();
	public static final IDbEnumField<DbTestObject, DbTestObjectFlag> ENUM_FIELD = BUILDER.addEnumField("enum", o->o.enumField, (o,v)->o.enumField=v, DbTestObjectFlag.class).setDefault(DbTestObjectFlag.SMALL);
	public static final IDbFloatField<DbTestObject> FLOAT_FIELD = BUILDER.addFloatField("float", o->o.floatValue, (o,v)->o.floatValue=v).setNullable();
	public static final IDbForeignField<DbTestObject, DbTestObject> FOREIGN_FIELD = BUILDER.addForeignField("fk", o->o.foreign, (o,v)->o.foreign=v, DbTestObject.ID_FIELD).setNullable();
	public static final IDbIntegerField<DbTestObject> INTEGER_FIELD = BUILDER.addIntegerField("integer", o->o.integerValue, (o,v)->o.integerValue=v).setDefault(DEFAULT_INTEGER_VALUE).setNullable();
	public static final IDbLongField<DbTestObject> LONG_FIELD = BUILDER.addLongField("long", o->o.longValue, (o,v)->o.longValue=v).setNullable();
	public static final IDbStringField<DbTestObject> STRING_FIELD = BUILDER.addStringField("string", o->o.string, (o,v)->o.string=v).setDefault(DEFAULT_STRING_VALUE).setNullable().setMaximumLength(255);
	public static final IDbTimeField<DbTestObject> TIME_FIELD = BUILDER.addTimeField("time", o->o.time, (o,v)->o.time=v).setNullable();
	public static final DbTestObjectTable TABLE = new DbTestObjectTable(BUILDER);
	// @formatter:on

	private Integer id;
	private BigDecimal decimal;
	private Boolean booleanValue;
	private byte[] bytes;
	private Day day;
	private DayTime dayTime;
	private Double doubleValue;
	private DbTestObjectFlag enumField;
	private Float floatValue;
	private DbTestObject foreign;
	private Integer integerValue;
	private Long longValue;
	private String string;
	private Time time;

	public DbTestObject() {

		// nothing to do
	}

	public DbTestObject(Integer id) {

		initializeFlags();
		this.id = id;
	}

	public DbTestObject(DbTestObject other) {

		initializer().initializeCopy(other);
	}

	public BigDecimal getBigDecimal() {

		return BIG_DECIMAL_FIELD.getValue(this);
	}

	public boolean setBigDecimal(BigDecimal value) {

		return BIG_DECIMAL_FIELD.setValue(this, value);
	}

	public Boolean getBoolean() {

		return BOOLEAN_FIELD.getValue(this);
	}

	public boolean setBoolean(Boolean value) {

		return BOOLEAN_FIELD.setValue(this, value);
	}

	public byte[] getBytes() {

		return BYTES_FIELD.getValue(this);
	}

	public boolean setBytes(byte[] value) {

		return BYTES_FIELD.setValue(this, value);
	}

	public Day getDay() {

		return DAY_FIELD.getValue(this);
	}

	public boolean setDay(Day value) {

		return DAY_FIELD.setValue(this, value);
	}

	public DayTime getDayTime() {

		return DAY_TIME_FIELD.getValue(this);
	}

	public boolean setDayTime(DayTime value) {

		return DAY_TIME_FIELD.setValue(this, value);
	}

	public Double getDouble() {

		return DOUBLE_FIELD.getValue(this);
	}

	public boolean setDouble(Double value) {

		return DOUBLE_FIELD.setValue(this, value);
	}

	public DbTestObjectFlag getEnum() {

		return ENUM_FIELD.getValue(this);
	}

	public boolean setEnum(DbTestObjectFlag value) {

		return ENUM_FIELD.setValue(this, value);
	}

	public Float getFloat() {

		return FLOAT_FIELD.getValue(this);
	}

	public boolean setFloat(Float value) {

		return FLOAT_FIELD.setValue(this, value);
	}

	public DbTestObject getForeign() {

		return FOREIGN_FIELD.getValue(this);
	}

	public boolean setForeign(DbTestObject value) {

		return FOREIGN_FIELD.setValue(this, value);
	}

	public Integer getInteger() {

		return INTEGER_FIELD.getValue(this);
	}

	public boolean setInteger(Integer value) {

		return INTEGER_FIELD.setValue(this, value);
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

	public Time getTime() {

		return TIME_FIELD.getValue(this);
	}

	public boolean setTime(Time value) {

		return TIME_FIELD.setValue(this, value);
	}

	@Override
	public DbObjectTable<DbTestObject> table() {

		return TABLE;
	}
}
