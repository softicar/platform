package com.softicar.platform.common.io.writer;

/**
 * This interface extends {@link IManagedWriter} with line printing methods.
 *
 * @author Oliver Richers
 */
public interface IManagedPrintWriter extends IManagedWriter {

	/**
	 * Calls {@link String#format(String, Object...)} and writes the result
	 * using {@link #write(CharSequence)}.
	 *
	 * @param format
	 *            the format {@link String} (never <i>null</i>)
	 * @param arguments
	 *            the formatting arguments (never <i>null</i>)
	 */
	default IManagedPrintWriter printf(String format, Object...arguments) {

		write(String.format(format, arguments));
		return this;
	}

	/**
	 * Prints a line separator.
	 */
	default IManagedPrintWriter println() {

		write(getLineSeparator());
		return this;
	}

	/**
	 * Calls {@link #printf(String, Object...)} and then {@link #println()}.
	 *
	 * @param format
	 *            the format {@link String} (never <i>null</i>)
	 * @param arguments
	 *            the formatting arguments (never <i>null</i>)
	 */
	default IManagedPrintWriter println(String format, Object...arguments) {

		printf(format, arguments);
		println();
		return this;
	}

	/**
	 * Returns the line separator of used by this {@link IManagedPrintWriter}.
	 *
	 * @return the line separator (never <i>null</i>)
	 */
	String getLineSeparator();
}
