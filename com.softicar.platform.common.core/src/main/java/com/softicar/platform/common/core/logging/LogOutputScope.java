package com.softicar.platform.common.core.logging;

import java.util.Objects;

/**
 * An {@link AutoCloseable} to conveniently control {@link CurrentLogOuput}.
 *
 * @author Oliver Richers
 */
public class LogOutputScope implements AutoCloseable {

	private final ILogOutput originalOutput;

	/**
	 * Remembers the currently defined value of {@link CurrentLogOuput#get} and
	 * defines the given {@link ILogOutput} using {@link CurrentLogOuput#set}.
	 *
	 * @param output
	 *            the new {@link ILogOutput} (never <i>null</i>)
	 */
	public LogOutputScope(ILogOutput output) {

		this.originalOutput = CurrentLogOuput.get();

		CurrentLogOuput.set(Objects.requireNonNull(output));
	}

	/**
	 * Resets {@link CurrentLogOuput} to its original value.
	 */
	@Override
	public void close() {

		CurrentLogOuput.set(originalOutput);
	}
}
