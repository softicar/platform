package com.softicar.platform.common.math;

/**
 * This class provides static utility method for unboxing primitive types.
 * <p>
 * In Java, every primitive type has a corresponding wrapper type. The primitive
 * type <i>int</i> for example has the wrapper type counterpart {@link Integer}.
 * Converting between a primitive type and the wrapper type is called boxing
 * (primitive type to wrapper type) an unboxing (wrapper type to primitive
 * type).
 * <p>
 * An important difference between a primitive type and its wrapper type
 * counterpart is that the wrapper type may be null. Because the primitive type
 * cannot represent a null value, unboxing a wrapper type may cause a
 * null-pointer exception. Hence, an extra check for null-pointers is often
 * necessary when unboxing such a wrapper type. This class provides methods to
 * define default values, which will be used if the unboxed wrapper object is a
 * null-pointer.
 * 
 * @author Oliver Richers
 */
public class Unboxing {

	// -------------------- GET VALUE -------------------- //

	/**
	 * Returns the <i>boolean</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>boolean</i> value of the given object, or the default
	 *         value if the wrapper object is null
	 */
	public static boolean getValue(Boolean object, boolean defaultValue) {

		return object != null? object.booleanValue() : defaultValue;
	}

	/**
	 * Returns the <i>byte</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>byte</i> value of the given object, or the default value
	 *         if the wrapper object is null
	 */
	public static byte getValue(Byte object, byte defaultValue) {

		return object != null? object.byteValue() : defaultValue;
	}

	/**
	 * Returns the <i>short</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>short</i> value of the given object, or the default value
	 *         if the wrapper object is null
	 */
	public static short getValue(Short object, short defaultValue) {

		return object != null? object.shortValue() : defaultValue;
	}

	/**
	 * Returns the <i>int</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>int</i> value of the given object, or the default value if
	 *         the wrapper object is null
	 */
	public static int getValue(Integer object, int defaultValue) {

		return object != null? object.intValue() : defaultValue;
	}

	/**
	 * Returns the <i>long</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>long</i> value of the given object, or the default value
	 *         if the wrapper object is null
	 */
	public static long getValue(Long object, long defaultValue) {

		return object != null? object.longValue() : defaultValue;
	}

	/**
	 * Returns the <i>float</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>float</i> value of the given object, or the default value
	 *         if the wrapper object is null
	 */
	public static float getValue(Float object, float defaultValue) {

		return object != null? object.floatValue() : defaultValue;
	}

	/**
	 * Returns the <i>double</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @param defaultValue
	 *            the default value to use if the wrapper object is null
	 * @return the <i>double</i> value of the given object, or the default value
	 *         if the wrapper object is null
	 */
	public static double getValue(Double object, double defaultValue) {

		return object != null? object.doubleValue() : defaultValue;
	}

	// -------------------- CONVENIENCE METHODS -------------------- //

	/**
	 * Returns the <i>boolean</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>boolean</i> value of the given object, or <i>false</i> if
	 *         the wrapper object is null
	 */
	public static boolean getValueOrFalse(Boolean object) {

		return getValue(object, false);
	}

	/**
	 * Returns the <i>byte</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>byte</i> value of the given object, or zero if the wrapper
	 *         object is null
	 */
	public static byte getValueOrZero(Byte object) {

		return getValue(object, (byte) 0);
	}

	/**
	 * Returns the <i>short</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>short</i> value of the given object, or zero if the
	 *         wrapper object is null
	 */
	public static short getValueOrZero(Short object) {

		return getValue(object, (short) 0);
	}

	/**
	 * Returns the <i>int</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>int</i> value of the given object, or zero if the wrapper
	 *         object is null
	 */
	public static int getValueOrZero(Integer object) {

		return getValue(object, 0);
	}

	/**
	 * Returns the <i>long</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>long</i> value of the given object, or zero if the wrapper
	 *         object is null
	 */
	public static long getValueOrZero(Long object) {

		return getValue(object, 0L);
	}

	/**
	 * Returns the <i>float</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>float</i> value of the given object, or zero if the
	 *         wrapper object is null
	 */
	public static float getValueOrZero(Float object) {

		return getValue(object, 0F);
	}

	/**
	 * Returns the <i>double</i> value of the given wrapper object.
	 * 
	 * @param object
	 *            the wrapper object
	 * @return the <i>double</i> value of the given object, or zero if the
	 *         wrapper object is null
	 */
	public static double getValueOrZero(Double object) {

		return getValue(object, 0D);
	}
}
