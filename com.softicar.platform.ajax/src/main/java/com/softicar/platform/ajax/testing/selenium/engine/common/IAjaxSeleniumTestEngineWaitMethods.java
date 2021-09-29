package com.softicar.platform.ajax.testing.selenium.engine.common;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Facilitates waiting for specific events or conditions during test execution.
 *
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumTestEngineWaitMethods {

	/**
	 * The default amount of time to wait.
	 */
	Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

	/**
	 * Pauses test execution until the given {@link Supplier} evaluates to
	 * <i>true</i>.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 *
	 * @param condition
	 *            the condition to check periodically (never <i>null</i>)
	 */
	default void waitUntil(Supplier<Boolean> condition) {

		waitUntil(condition, DEFAULT_TIMEOUT);
	}

	/**
	 * Pauses test execution until the given {@link Supplier} evaluates to
	 * <i>true</i>.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param condition
	 *            the condition to check periodically (never <i>null</i>)
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	default void waitUntil(Supplier<Boolean> condition, Duration timeout) {

		new FluentWait<>(0)//
			.withTimeout(timeout)
			.until(dummy -> condition.get());
	}

	/**
	 * Pauses test execution until the given {@link Predicate} evaluates to
	 * <i>true</i> for the given object.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 *
	 * @param object
	 *            the object to check periodically (never <i>null</i>)
	 * @param predicate
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 */
	default <T> void waitUntil(T object, Predicate<T> predicate) {

		waitUntil(object, predicate, DEFAULT_TIMEOUT);
	}

	/**
	 * Pauses test execution until the given {@link Predicate} evaluates to
	 * <i>true</i> for the given object.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param object
	 *            the object to check periodically (never <i>null</i>)
	 * @param predicate
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	default <T> void waitUntil(T object, Predicate<T> predicate, Duration timeout) {

		new FluentWait<>(0)//
			.withTimeout(timeout)
			.until(dummy -> predicate.test(object));
	}

	/**
	 * Pauses test execution until the given {@link Supplier} returns a
	 * non-<i>null</i> object.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 *
	 * @param objectSupplier
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 */
	default <T> T waitUntilSupplied(Supplier<T> objectSupplier) {

		return waitUntilSupplied(objectSupplier, DEFAULT_TIMEOUT);
	}

	/**
	 * Pauses test execution until the given {@link Supplier} returns a
	 * non-<i>null</i> object.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param objectSupplier
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	default <T> T waitUntilSupplied(Supplier<T> objectSupplier, Duration timeout) {

		return new FluentWait<>(0)//
			.withTimeout(timeout)
			.until(dummy -> objectSupplier.get());
	}

	/**
	 * Pauses test execution until the given {@link Supplier} evaluates to
	 * <i>false</i>.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 *
	 * @param condition
	 *            the condition to check periodically (never <i>null</i>)
	 */
	default void waitWhile(Supplier<Boolean> condition) {

		waitWhile(condition, DEFAULT_TIMEOUT);
	}

	/**
	 * Pauses test execution until the given {@link Supplier} evaluates to
	 * <i>false</i>.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param condition
	 *            the condition to check periodically (never <i>null</i>)
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	default void waitWhile(Supplier<Boolean> condition, Duration timeout) {

		new FluentWait<>(0)//
			.withTimeout(timeout)
			.until(dummy -> !condition.get());
	}

	/**
	 * Pauses test execution until the given {@link Predicate} evaluates to
	 * <i>true</i> for the given object.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 *
	 * @param object
	 *            the object to check periodically (never <i>null</i>)
	 * @param predicate
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 */
	default <T> void waitWhile(T object, Predicate<T> predicate) {

		waitWhile(object, predicate, DEFAULT_TIMEOUT);
	}

	/**
	 * Pauses test execution until the given {@link Predicate} evaluates to
	 * <i>false</i> for the given object.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param object
	 *            the object to check periodically (never <i>null</i>)
	 * @param predicate
	 *            the predicate to evaluate periodically (never <i>null</i>)
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	default <T> void waitWhile(T object, Predicate<T> predicate, Duration timeout) {

		new FluentWait<>(0)//
			.withTimeout(timeout)
			.until(dummy -> !predicate.test(object));
	}

	/**
	 * Waits until the server has finished asynchronous processing of an action.
	 * <p>
	 * If this does not happen within the {@link #DEFAULT_TIMEOUT}
	 * {@link Duration}, an {@link Exception} is thrown.
	 */
	default void waitForServer() {

		waitForServer(DEFAULT_TIMEOUT);
	}

	/**
	 * Waits until the server has finished asynchronous processing of an action.
	 * <p>
	 * If this does not happen within the specified timeout {@link Duration}, an
	 * {@link Exception} is thrown.
	 *
	 * @param timeout
	 *            the amount of time before giving up (never <i>null</i>)
	 */
	void waitForServer(Duration timeout);
}
