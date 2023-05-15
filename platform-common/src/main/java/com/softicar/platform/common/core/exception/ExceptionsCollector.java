package com.softicar.platform.common.core.exception;

import com.softicar.platform.common.core.throwable.Throwables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Collects {@link Exception} objects and aggregates them into a single
 * {@link Exception}.
 *
 * @author Oliver Richers
 */
public class ExceptionsCollector {

	private final Collection<Throwable> exceptions;
	private String preamble;

	public ExceptionsCollector() {

		this.exceptions = new ArrayList<>();
		this.preamble = "";
	}

	public ExceptionsCollector setPreamble(String preamble, Object...arguments) {

		this.preamble = String.format(preamble, arguments);
		return this;
	}

	/**
	 * Adds the given {@link Exception} to this {@link ExceptionsCollector}.
	 * <p>
	 * If the given {@link Exception} is an {@link InterruptedException} or
	 * caused by one, {@link Thread#interrupt()} will be called on the current
	 * thread.
	 *
	 * @param exception
	 *            the {@link Exception} (never <i>null</i>)
	 * @return this
	 */
	public ExceptionsCollector add(Throwable exception) {

		exceptions.add(Objects.requireNonNull(exception));
		if (Throwables.isCausedBy(InterruptedException.class, exception)) {
			throw new RuntimeException(
				"%s encountered an %s.".formatted(ExceptionsCollector.class.getSimpleName(), InterruptedException.class.getSimpleName()),
				exception);
		}
		return this;
	}

	/**
	 * Returns whether this {@link ExceptionsCollector} is empty.
	 *
	 * @return <i>true</i> if no exceptions were added; <i>false</i> otherwise
	 */
	public boolean isEmpty() {

		return exceptions.isEmpty();
	}

	/**
	 * Returns all added {@link Exception} objects.
	 *
	 * @return all added {@link Exception} objects (never <i>null</i>)
	 */
	public Collection<Throwable> getExceptions() {

		return exceptions;
	}

	/**
	 * Returns a combined error message for all added {@link Exception} objects.
	 *
	 * @return the error message (never <i>null</i>)
	 */
	public String getMessage() {

		return preamble + new MultiExceptionMessageBuilder(exceptions).buildMessage();
	}

	/**
	 * Throws a single {@link MultiException} containing information about all
	 * added {@link Exception} objects.
	 * <p>
	 * If no exception was added, this method does nothing.
	 */
	public void throwIfNotEmpty() {

		if (!isEmpty()) {
			throw new MultiException(getMessage(), exceptions);
		}
	}

	/**
	 * Calls the {@link Consumer} with the return value of
	 * {@link #getMessage()}.
	 * <p>
	 * If no exception was added, this method does nothing.
	 */
	public void applyIfNotEmpty(Consumer<String> messageConsumer) {

		if (!isEmpty()) {
			messageConsumer.accept(getMessage());
		}
	}
}
