package com.softicar.platform.common.testing;

import com.softicar.platform.common.container.iterable.Iterables;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Assert;

/**
 * Provides convenience methods related to assertions in JUnit tests.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class Asserts extends Assert {

	public void assertEquals(int expectedValue, Integer actualValue) {

		assertEquals(Integer.valueOf(expectedValue), actualValue);
	}

	public <T> void assertSame(T expectedObject, Optional<T> optional) {

		assertTrue(optional.isPresent());
		assertSame(expectedObject, optional.get());
	}

	// --------------------------- exceptions --------------------------- //

	/**
	 * Asserts that an exception is thrown by the given function.
	 *
	 * @param expectedThrowableClass
	 *            the expected exception class, a super-class or interface
	 *            thereof (never <i>null</i>)
	 * @param thrower
	 *            the function which is expected to throw an exception of the
	 *            given type (never <i>null</i>)
	 */
	public static void assertException(Class<? extends Throwable> expectedThrowableClass, INullaryVoidFunction thrower) {

		assertException(expectedThrowableClass, thrower, null);
	}

	/**
	 * Asserts that an exception is thrown by the given function.
	 *
	 * @param expectedThrowableClass
	 *            the expected exception class, a super-class or interface
	 *            thereof (never <i>null</i>)
	 * @param thrower
	 *            the function which is expected to throw an exception of the
	 *            given type (never <i>null</i>)
	 * @param expectedMessage
	 *            the expected exception message (may be <i>null</i>)
	 */
	public static void assertException(Class<? extends Throwable> expectedThrowableClass, INullaryVoidFunction thrower, String expectedMessage) {

		Objects.requireNonNull(expectedThrowableClass);
		Objects.requireNonNull(thrower);
		Throwable thrown = null;
		try {
			thrower.apply();
		} catch (Exception throwable) {
			thrown = throwable;
		}
		assertNotNull(String.format("Expected a Throwable of class %s but none was thrown.", expectedThrowableClass.getCanonicalName()), thrown);
		Class<? extends Throwable> thrownClass = thrown.getClass();
		assertTrue(
			String
				.format(
					"Expected a Throwable of class %s but encountered a %s: %s",
					expectedThrowableClass.getCanonicalName(),
					thrownClass.getCanonicalName(),
					StackTraceFormatting.getStackTraceAsString(thrown)),
			expectedThrowableClass.isAssignableFrom(thrownClass));
		if (expectedMessage != null) {
			assertEquals("Unexpected exception message.", expectedMessage, thrown.getMessage());
		}
	}

	/**
	 * @deprecated use {@link #assertException(Class, INullaryVoidFunction)}
	 *             instead
	 */
	@Deprecated
	public static void assertThrows(Class<? extends Throwable> expectedThrowableClass, INullaryVoidFunction thrower) {

		assertException(expectedThrowableClass, thrower);
	}

	public static void assertException(INullaryVoidFunction thrower, IDisplayString expectedMessage) {

		try {
			thrower.apply();
			fail("An expected exception failed to occur.");
		} catch (Exception exception) {
			assertEquals(expectedMessage.toString(), getNonNullMessageOrFail(exception));
		}
	}

	/**
	 * @deprecated use
	 *             {@link #assertException(INullaryVoidFunction, IDisplayString)}
	 *             instead
	 */
	@Deprecated
	public static void assertExceptionMessage(IDisplayString expectedMessage, INullaryVoidFunction thrower) {

		assertException(thrower, expectedMessage);
	}

	public static void assertExceptionMessageContains(INullaryVoidFunction thrower, IDisplayString expectedMessage) {

		try {
			thrower.apply();
			fail("An expected exception failed to occur.");
		} catch (Exception exception) {
			String message = getNonNullMessageOrFail(exception);
			assertTrue(//
				"The expected text\n\"%s\"\n is not contained in the encountered exception message:\n\"%s\"".formatted(expectedMessage.toString(), message),
				exception.getMessage().contains(expectedMessage.toString()));
		}
	}

	/**
	 * @deprecated use
	 *             {@link #assertExceptionMessageContains(INullaryVoidFunction, IDisplayString)}
	 *             instead
	 */
	@Deprecated
	public static void assertExceptionMessageContains(IDisplayString expectedMessage, INullaryVoidFunction thrower) {

		assertExceptionMessageContains(thrower, expectedMessage);
	}

	// --------------------------- assertCount --------------------------- //

	public static <T> List<T> assertCount(int expectedCount, Iterable<T> objects, Predicate<T> predicate) {

		List<T> matchingObjects = getMatchingObjects(objects, predicate);
		assertEquals(expectedCount, matchingObjects.size());
		return matchingObjects;
	}

	public static <T> List<T> assertCount(int expectedCount, Iterable<T> objects) {

		return assertCount(expectedCount, objects, it -> true);
	}

	public static <T> T assertOne(Iterable<T> objects, Predicate<T> predicate) {

		return assertCount(1, objects, predicate).get(0);
	}

	public static <T> void assertAny(Iterable<T> objects, Predicate<T> predicate) {

		List<T> matchingObjects = getMatchingObjects(objects, predicate);
		assertFalse(//
			"None of the given objects satisfies the given predicate.",
			matchingObjects.isEmpty());
	}

	public static <T> List<T> assertAll(Iterable<T> objects, Predicate<T> predicate) {

		Collection<T> objectsCollection = Iterables.toCollection(objects);
		List<T> matchingObjects = getMatchingObjects(objectsCollection, predicate);
		assertEquals(//
			"At least one of the given objects fails to satisfy the given predicate.",
			objectsCollection.size(),
			matchingObjects.size());
		return matchingObjects;
	}

	public static <T> T assertOne(Iterable<T> objects) {

		return assertCount(1, objects).get(0);
	}

	public static <T> void assertNone(Iterable<T> objects) {

		assertCount(0, objects);
	}

	// --------------------------- assertRegexp --------------------------- //

	public static void assertRegex(String regex, String fullText) {

		boolean matches = Pattern//
			.compile(regex, Pattern.DOTALL)
			.matcher(fullText)
			.matches();
		if (!matches) {
			throw new AssertionError(String.format("The string '%s' does not match regular expression '%s'.", fullText, regex));
		}
	}

	// --------------------------- assertContains --------------------------- //

	public static void assertContains(String substring, String fullText) {

		if (!fullText.contains(substring)) {
			throw new AssertionError(String.format("Failed to find substring '%s' in '%s'.", substring, fullText));
		}
	}

	// --------------------------- assertEmpty --------------------------- //

	public static void assertEmpty(Optional<?> optional) {

		if (!optional.isEmpty()) {
			throw new AssertionError("Expected optional to be empty.");
		}
	}

	// --------------------------- assertStartsWith --------------------------- //

	public static void assertStartsWith(String start, String fullText) {

		if (!fullText.startsWith(start)) {
			throw new AssertionError(String.format("Failed to find '%s' at beginning of '%s'.", start, fullText));
		}
	}

	// --------------------------- private helpers --------------------------- //

	private static <T> List<T> getMatchingObjects(Iterable<T> objects, Predicate<T> predicate) {

		return Iterables//
			.toStream(objects)
			.filter(predicate)
			.collect(Collectors.toList());
	}

	private static String getNonNullMessageOrFail(Exception exception) {

		String message = exception.getMessage();
		if (message != null) {
			return message;
		} else {
			throw new AssertionError("The encountered %s does not have a message.".formatted(exception.getClass().getSimpleName()));
		}
	}
}
