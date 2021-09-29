package com.softicar.platform.db.core;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This class contains static method for casting database value types.
 * <p>
 * This class is primarily used by {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbCasting {

	/**
	 * Casts the given object into a number.
	 *
	 * @param object
	 *            a number object
	 * @return the given object as number
	 */
	public static Number getNumber(Object object) {

		return (Number) object;
	}

	/**
	 * Converts the given object into {@link Boolean}.
	 * <p>
	 * <ul>
	 * <li>If the object is null, this returns null.</li>
	 * <li>If the object is a boolean it is cast into {@link Boolean},</li>
	 * <li>If the object is a {@link Number}, its integer value is converted
	 * into a boolean, using the standard integer to boolean conversion rule of
	 * <code>intValue() != 0</code>.</li>
	 * </ul>
	 *
	 * @param object
	 *            the object to convert into boolean
	 * @return the given object as boolean
	 */
	public static Boolean getBoolean(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Boolean) {
			return (Boolean) object;
		} else {
			return ((Number) object).intValue() != 0? true : false;
		}
	}

	/**
	 * Converts the given object into {@link Integer}.
	 * <ul>
	 * <li>If the object is null, this returns null.</li>
	 * <li>If the object is an integer, it is cast into {@link Integer}.</li>
	 * <li>If the object is a boolean, it is converted into an integer, with the
	 * standard rule that <i>true</i> is <i>1</i> and <i>false</i> is <i>0</i>.
	 * </li>
	 * <li>If the object is string, the string is parsed into an integer.</li>
	 * <li>Else, the object is cast into {@link Number} and the integer value of
	 * the number is returned.</li>
	 * </ul>
	 *
	 * @param object
	 *            the object to convert into integer
	 * @return the given object as integer
	 */
	public static Integer getInteger(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Integer) {
			return (Integer) object;
		} else if (object instanceof Boolean) {
			return ((Boolean) object).booleanValue()? 1 : 0;
		} else if (object instanceof String) {
			return Integer.parseInt((String) object);
		} else {
			return getNumber(object).intValue();
		}
	}

	/**
	 * Converts the given object into {@link Long}.
	 * <ul>
	 * <li>If the object is null, this returns null.</li>
	 * <li>If the object is a long, it is cast into {@link Long}.</li>
	 * <li>If the object is a boolean, it is converted into an integer, with the
	 * standard rule that <i>true</i> is <i>1</i> and <i>false</i> is <i>0</i>.
	 * </li>
	 * <li>If the object is string, the string is parsed into a long.</li>
	 * <li>Else, the object is cast into {@link Number} and the long value of
	 * the number is returned.</li>
	 * </ul>
	 *
	 * @param object
	 *            the object to convert into long
	 * @return the given object as long
	 */
	public static Long getLong(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Long) {
			return (Long) object;
		} else if (object instanceof Boolean) {
			return ((Boolean) object).booleanValue()? 1L : 0L;
		} else if (object instanceof String) {
			return Long.parseLong((String) object);
		} else {
			return getNumber(object).longValue();
		}
	}

	/**
	 * Converts the given object into {@link Double}.
	 * <ul>
	 * <li>If the object is null, this returns null.</li>
	 * <li>If the object is a double, it is cast into {@link Double}.</li>
	 * <li>Else, the object is cast into {@link Number} and the double value of
	 * the number is returned.</li>
	 * </ul>
	 *
	 * @param object
	 *            the object to convert into double
	 * @return the given object as double
	 */
	public static Double getDouble(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Double) {
			return (Double) object;
		} else {
			return getNumber(object).doubleValue();
		}
	}

	/**
	 * Converts the given object into {@link Float}.
	 * <ul>
	 * <li>If the object is null, this returns null.</li>
	 * <li>If the object is a float, it is cast into {@link Float}.</li>
	 * <li>Else, the object is cast into {@link Number} and the float value of
	 * the number is returned.</li>
	 * </ul>
	 *
	 * @param object
	 *            the object to convert into float
	 * @return the given object as float
	 */
	public static Float getFloat(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Float) {
			return (Float) object;
		} else {
			return getNumber(object).floatValue();
		}
	}

	public static BigDecimal getBigDecimal(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof BigDecimal) {
			return (BigDecimal) object;
		} else if (object instanceof String) {
			return new BigDecimal((String) object);
		} else if (object instanceof Double) {
			return BigDecimal.valueOf((Double) object);
		} else if (object instanceof Float) {
			return BigDecimal.valueOf((Float) object);
		} else if (object instanceof Integer) {
			return BigDecimal.valueOf((Integer) object);
		} else {
			throw new SofticarException("Can't convert value %s of type %s to BigDecimal.", object, object.getClass());
		}
	}

	public static Date getDate(Object object) {

		return (Date) object;
	}

	public static Day getDay(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Day) {
			return (Day) object;
		} else {
			return Day.fromDate(getDate(object));
		}
	}

	public static DayTime getDayTime(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof DayTime) {
			return (DayTime) object;
		} else {
			return DayTime.fromDate(getDate(object));
		}
	}

	@SuppressWarnings("deprecation")
	public static Time getTime(Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Time) {
			return (Time) object;
		} else if (object instanceof java.sql.Time) {
			java.sql.Time time = (java.sql.Time) object;
			return new Time(time.getHours(), time.getMinutes(), time.getSeconds());
		} else if (object instanceof Timestamp) {
			Timestamp timestamp = (Timestamp) object;
			return new Time(timestamp.getHours(), timestamp.getMinutes(), timestamp.getSeconds());
		} else {
			return Time.parseTime((String) object);
		}
	}
}
