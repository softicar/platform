package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.code.java.JavaClass;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import java.math.BigDecimal;

public enum SqlFieldType {

	BIG_DECIMAL(SqlValueTypes.BIG_DECIMAL, BigDecimal.class),
	BOOLEAN(SqlValueTypes.BOOLEAN, Boolean.class),
	BYTE_ARRAY(SqlValueTypes.BYTE_ARRAY, byte[].class),
	DAY(SqlValueTypes.DAY, Day.class),
	DAY_TIME(SqlValueTypes.DAY_TIME, DayTime.class),
	DOUBLE(SqlValueTypes.DOUBLE, Double.class),
	ENUM(null, null),
	FLOAT(SqlValueTypes.FLOAT, Float.class),
	INTEGER(SqlValueTypes.INTEGER, Integer.class),
	LONG(SqlValueTypes.LONG, Long.class),
	STRING(SqlValueTypes.STRING, String.class),
	TIME(SqlValueTypes.TIME, Time.class);

	private final ISqlValueType<?> valueType;
	private final JavaClass javaClass;

	private SqlFieldType(ISqlValueType<?> valueType, Class<?> valueClass, Class<?>...otherClasses) {

		this.valueType = valueType;
		this.javaClass = createJavaClass(valueClass, otherClasses);
	}

	@SuppressWarnings("unchecked")
	public <V> ISqlValueType<V> getValueType() {

		return (ISqlValueType<V>) valueType;
	}

	public JavaClass getJavaClass() {

		return javaClass;
	}

	private JavaClass createJavaClass(Class<?> valueClass, Class<?>...otherClasses) {

		JavaClass javaClass = null;

		if (valueClass != null) {
			javaClass = new JavaClass(valueClass);
			for (Class<?> otherClass: otherClasses) {
				javaClass.addImport(otherClass);
			}
		}

		return javaClass;
	}
}
