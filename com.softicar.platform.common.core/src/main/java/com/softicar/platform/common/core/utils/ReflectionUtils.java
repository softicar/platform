package com.softicar.platform.common.core.utils;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class contains static utility methods for Java reflection.
 *
 * @author Oliver Richers
 */
public class ReflectionUtils {

	/**
	 * Creates a new instance of the specified class.
	 * <p>
	 * The purpose of this method is to catch all checked exceptions and to
	 * convert them to {@link SofticarDeveloperException}.
	 *
	 * @param theClass
	 *            the class to instantiate, the class must provide a default
	 *            constructor
	 * @return the created object, never null
	 */
	public static <T> T newInstance(Class<T> theClass) {

		try {
			Constructor<T> ctor = theClass.getDeclaredConstructor();
			ctor.setAccessible(true);
			return ctor.newInstance();
		} catch (InstantiationException exception) {
			throw new SofticarDeveloperException(exception);
		} catch (IllegalAccessException exception) {
			throw new SofticarDeveloperException(exception);
		} catch (NoSuchMethodException exception) {
			throw new SofticarDeveloperException(exception);
		} catch (InvocationTargetException exception) {
			throw convertInvocationTargetExceptionToRuntimeException(exception);
		}
	}

	public static <T> T newInstance(Constructor<T> constructor, Object...parameters) {

		try {
			return constructor.newInstance(parameters);
		} catch (InstantiationException exception) {
			throw new SofticarDeveloperException(exception, "Failed to instantiate class %s.", getClassName(constructor));
		} catch (IllegalAccessException exception) {
			throw new SofticarDeveloperException(exception, "Not allowed to instantiate class %s. Maybe not public?", getClassName(constructor));
		} catch (InvocationTargetException exception) {
			throw convertInvocationTargetExceptionToRuntimeException(exception);
		}
	}

	public static RuntimeException convertInvocationTargetExceptionToRuntimeException(InvocationTargetException exception) {

		Throwable throwable = exception.getCause();
		if (throwable instanceof RuntimeException) {
			return (RuntimeException) throwable;
		} else {
			return new SofticarDeveloperException(throwable != null? throwable : exception);
		}
	}

	private static <T> String getClassName(Constructor<T> constructor) {

		return constructor.getDeclaringClass().getCanonicalName();
	}

	public static <T> Constructor<T> getConstructorOrNull(Class<T> theClass, Class<?>...parameterTypes) {

		try {
			return theClass.getConstructor(parameterTypes);
		} catch (NoSuchMethodException exception) {
			DevNull.swallow(exception);
			return null;
		}
	}

	/**
	 * Tries to load the class with the specified name with the given class
	 * loader.
	 *
	 * @param classLoader
	 *            the class loader to use for loading
	 * @param className
	 *            the canonical class name
	 * @return the loaded class or null if no such class could be loaded
	 */
	public static <T> Class<T> tryToLoadClass(ClassLoader classLoader, String className) {

		try {
			return CastUtils.cast(classLoader.loadClass(className));
		} catch (ClassNotFoundException exception) {
			DevNull.swallow(exception);
			return null;
		}
	}

	/**
	 * Returns true if the specified class provides a default constructor.
	 */
	public static <T> boolean hasDefaultConstructor(Class<T> theClass) {

		try {
			return theClass.getDeclaredConstructor() != null;
		} catch (NoSuchMethodException exception) {
			DevNull.swallow(exception);
			return false;
		}
	}

	/**
	 * Returns the matching method of the given class.
	 *
	 * @param someClass
	 *            the class providing the method
	 * @param methodName
	 *            the name of the method
	 * @param parameters
	 *            the parameters of the method
	 * @return the matching method, never null
	 * @throws RuntimeException
	 *             if {@link NoSuchMethodException} was thrown
	 */
	public static Method getMethod(Class<?> someClass, String methodName, Class<?>...parameters) {

		try {
			return someClass.getMethod(methodName, parameters);
		} catch (NoSuchMethodException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Invokes the given method on the specified object.
	 *
	 * @param method
	 *            the method to invoke
	 * @param object
	 *            the object on which to invoke the method, or null if this is a
	 *            static method
	 * @param args
	 *            the arguments to the method
	 * @return the result of the method call
	 * @throws RuntimeException
	 *             if {@link IllegalAccessException} or
	 *             {@link InvocationTargetException} was thrown
	 */
	public static Object invokeMethod(Method method, Object object, Object...args) {

		try {
			return method.invoke(object, args);
		} catch (IllegalAccessException | InvocationTargetException exception) {
			throw new RuntimeException(exception);
		}
	}

	// -------------------------------- fields -------------------------------- //

	/**
	 * Determines whether the given {@link Field} carries each of the "public",
	 * "static" and "final" modifiers.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @return <i>true</i> if the {@link Field} is "public static final";
	 *         <i>false</i> otherwise
	 */
	public static boolean isPublicStaticFinal(Field field) {

		return isPublic(field) && isStatic(field) && isFinal(field);
	}

	/**
	 * Determines whether the given {@link Field} carries both the "static" and
	 * "final" modifiers.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @return <i>true</i> if the {@link Field} is "static final"; <i>false</i>
	 *         otherwise
	 */
	public static boolean isStaticFinal(Field field) {

		return isStatic(field) && isFinal(field);
	}

	/**
	 * Determines whether the given {@link Field} carries the "public" modifier.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @return <i>true</i> if the {@link Field} is "public"; <i>false</i>
	 *         otherwise
	 */
	public static boolean isPublic(Field field) {

		return Modifier.isPublic(field.getModifiers());
	}

	/**
	 * Determines whether the given {@link Field} carries the "static" modifier.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @return <i>true</i> if the {@link Field} is "static"; <i>false</i>
	 *         otherwise
	 */
	public static boolean isStatic(Field field) {

		return Modifier.isStatic(field.getModifiers());
	}

	/**
	 * Determines whether the given {@link Field} carries the "final" modifier.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @return <i>true</i> if the {@link Field} is "final"; <i>false</i>
	 *         otherwise
	 */
	public static boolean isFinal(Field field) {

		return Modifier.isFinal(field.getModifiers());
	}

	/**
	 * Determines whether the given {@link Field} has a declared type that
	 * corresponds to the given {@link Class}.
	 *
	 * @param field
	 *            the {@link Field} to test (never <i>null</i>)
	 * @param expectedType
	 *            the suspected type (never <i>null</i>)
	 * @return <i>true</i> if the given {@link Field} has given type;
	 *         <i>false</i> otherwise
	 */
	public static boolean isDeclaredType(Field field, Class<?> expectedType) {

		return expectedType.isAssignableFrom(field.getType());
	}

	/**
	 * Returns the value of the given static {@link Field}.
	 *
	 * @param staticField
	 *            the static field (never <i>null</i>)
	 * @return the value of the {@link Field} (may be <i>null</i>)
	 */
	public static Object getStaticValue(Field staticField) {

		try {
			return staticField.get(null);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
}
