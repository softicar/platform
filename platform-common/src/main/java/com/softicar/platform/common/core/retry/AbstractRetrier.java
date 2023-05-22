package com.softicar.platform.common.core.retry;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.core.throwable.Throwables;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Function;

/**
 * Abstract utility class to retry the execution of a method.
 * <p>
 * Please note that in case of an {@link InterruptedException} or an exception
 * caused by an {@link InterruptedException}, a retry will not be performed.
 *
 * @author Oliver Richers
 */
public abstract class AbstractRetrier<T extends AbstractRetrier<T>> {

	public static final int DEFAULT_TRY_COUNT = 3;
	public static final Duration DEFAULT_RETRY_DELAY = Duration.ZERO;
	private int tryCount;
	private Duration retryDelay;
	private Function<Exception, Boolean> retryDecider;

	public AbstractRetrier() {

		this.tryCount = DEFAULT_TRY_COUNT;
		this.retryDelay = DEFAULT_RETRY_DELAY;
		this.retryDecider = exception -> true;
	}

	/**
	 * Defines the number of tries.
	 * <p>
	 * This is the maximum number of tries that the given payload method is
	 * executed. The minimum is 1 and the default is 3.
	 *
	 * @param tryCount
	 *            the number of tries (minimum of 1)
	 * @return this object
	 * @throws IllegalArgumentException
	 *             if the try count is less than one
	 */
	public T setTryCount(int tryCount) {

		if (tryCount < 1) {
			throw new IllegalArgumentException(String.format("Illegal maximum try count of %s. Must be greater or equal to 1.", tryCount));
		}

		this.tryCount = tryCount;

		return getThis();
	}

	/**
	 * Defines the number of retries.
	 * <p>
	 * This is the maximum number of retries for the given payload method. The
	 * minimum is 0.
	 * <p>
	 * This is only a convenience method, which actually calls
	 * {@link #setTryCount(int)} with the count increased by one.
	 *
	 * @param retryCount
	 *            the number of tries (minimum of 0)
	 * @return this object
	 * @throws IllegalArgumentException
	 *             if the retry count is negative
	 */
	public T setRetryCount(int retryCount) {

		if (retryCount < 0) {
			throw new IllegalArgumentException(String.format("Illegal retry count of %s. Must be greater or equal to 0.", tryCount));
		}

		return setTryCount(retryCount + 1);
	}

	/**
	 * Defines the {@link Duration} to wait after a failed execution, before
	 * trying again.
	 *
	 * @param retryDelay
	 *            the retry delay (never <i>null</i> or negative); the default
	 *            is {@link Duration#ZERO}
	 * @throws IllegalArgumentException
	 *             if the delay is negative
	 * @return this object
	 */
	public T setRetryDelay(Duration retryDelay) {

		if (retryDelay.isNegative()) {
			throw new IllegalArgumentException(String.format("The retry delay may not be negative."));
		}
		this.retryDelay = retryDelay;
		return getThis();
	}

	/**
	 * Defines a function to check for a caught exception if the execution shall
	 * be tried again.
	 * <p>
	 * This is useful for example if you only want to retry the execution in
	 * case of an {@link IOException}.
	 * <p>
	 * Please note that in case of an {@link InterruptedException} or an
	 * exception caused by an {@link InterruptedException}, a retry will not be
	 * performed.
	 *
	 * @param retryDecider
	 *            the deciding function
	 * @return this object
	 */
	public T setRetryDecider(Function<Exception, Boolean> retryDecider) {

		Objects.nonNull(retryDecider);

		this.retryDecider = retryDecider;

		return getThis();
	}

	protected void executeRetryLoop() {

		int i = 1;
		while (true) {
			try {
				executeTry();
				return;
			} catch (Exception exception) {
				if (Throwables.isCausedBy(InterruptedException.class, exception)) {
					throw exception;
				} else if (i < tryCount && retryDecider.apply(exception)) {
					++i;
				} else {
					throw exception;
				}
			}

			if (!retryDelay.isZero()) {
				Sleep.sleep(retryDelay);
			}
		}
	}

	protected abstract void executeTry();

	protected abstract T getThis();
}
