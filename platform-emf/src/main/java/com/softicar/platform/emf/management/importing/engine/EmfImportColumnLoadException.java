package com.softicar.platform.emf.management.importing.engine;

public class EmfImportColumnLoadException extends RuntimeException {

	private final EmfImportColumn<?, ?> column;

	/**
	 * @param column
	 *            the column that actually should store the value, but that
	 *            could not be loaded
	 */
	public EmfImportColumnLoadException(EmfImportColumn<?, ?> column) {

		this.column = column;
	}

	/**
	 * @return The column that actually should store the value, but that could
	 *         not be loaded what triggered this
	 *         {@link EmfImportColumnLoadException}
	 */
	public EmfImportColumn<?, ?> getColumn() {

		return column;
	}
}
