package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.ISqlTable;
import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * This class enumerates all supported SQL value types.
 * <p>
 * Factory methods for values types of foreign fields, enumeration fields and
 * set fields are also provided.
 *
 * @author Oliver Richers
 */
public class SqlValueTypes {

	public static final ISqlValueType<Boolean> BOOLEAN = new SqlBooleanType();
	public static final ISqlValueType<BigDecimal> BIG_DECIMAL = new SqlBigDecimalType();
	public static final ISqlValueType<byte[]> BYTE_ARRAY = new SqlByteArrayType();
	public static final ISqlValueType<DayTime> DAY_TIME = new SqlDayTimeType();
	public static final ISqlValueType<Day> DAY = new SqlDayType();
	public static final ISqlValueType<Double> DOUBLE = new SqlDoubleType();
	public static final ISqlValueType<Float> FLOAT = new SqlFloatType();
	public static final ISqlValueType<Integer> INTEGER = new SqlIntegerType();
	public static final ISqlValueType<Long> LONG = new SqlLongType();
	public static final ISqlValueType<String> STRING = new SqlStringType();
	public static final ISqlValueType<Time> TIME = new SqlTimeType();

	public static <F> ISqlValueType<F> createForeign(Supplier<ISqlTable<F>> tableGetter) {

		return new SqlForeignValueType<>(tableGetter);
	}

	public static <E extends Enum<E>> ISqlValueType<E> createEnumType(Class<E> enumClass) {

		return new SqlEnumValueType<>(enumClass);
	}
}
