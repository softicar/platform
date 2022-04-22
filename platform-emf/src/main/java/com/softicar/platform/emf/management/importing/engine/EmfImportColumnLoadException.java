package com.softicar.platform.emf.management.importing.engine;

public class EmfImportColumnLoadException extends RuntimeException {

	private final EmfImportColumn<?, ?> column;

	public EmfImportColumnLoadException(EmfImportColumn<?, ?> column) {

		this.column = column;
	}

	public EmfImportColumn<?, ?> getColumn() {

		return column;
	}
}
