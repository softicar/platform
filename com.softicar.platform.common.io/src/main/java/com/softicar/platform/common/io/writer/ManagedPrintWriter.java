package com.softicar.platform.common.io.writer;

/**
 * Default implementation of {@link IManagedPrintWriter}.
 *
 * @author Oliver Richers
 */
public class ManagedPrintWriter extends ManagedWriter implements IManagedPrintWriter {

	private final String lineSeparator;

	public ManagedPrintWriter(Appendable appendable, String lineSeparator) {

		super(appendable);

		this.lineSeparator = lineSeparator;
	}

	@Override
	public String getLineSeparator() {

		return lineSeparator;
	}
}
