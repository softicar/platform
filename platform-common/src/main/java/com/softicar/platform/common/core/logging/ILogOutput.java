package com.softicar.platform.common.core.logging;

/**
 * The interface of the output object used in {@link CurrentLogOuput}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface ILogOutput {

	/**
	 * Logs the given line to this {@link ILogOutput}.
	 *
	 * @param line
	 *            the line to log (never <i>null</i>)
	 */
	void logLine(String line);
}
