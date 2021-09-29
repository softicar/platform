package com.softicar.platform.emf.data.table.export.precondition;

import com.softicar.platform.common.core.i18n.IDisplayString;

public class TableExportPreconditionResult {

	public static enum Level {
		ERROR,
		INFO,
		WARNING
	}

	// ----

	private final Level level;
	private final IDisplayString message;

	public TableExportPreconditionResult(Level level, IDisplayString message) {

		this.level = level;
		this.message = message;
	}

	public Level getLevel() {

		return level;
	}

	public IDisplayString getMessage() {

		return message;
	}
}
