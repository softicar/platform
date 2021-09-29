package com.softicar.platform.db.structure.utils;

/**
 * Parser for column comments.
 *
 * @author Oliver Richers
 */
public final class DbColumnCommentParser {

	private String nameOverride;
	private boolean ignoreOverride;
	private boolean doubleOverride;
	private boolean baseColumn;

	public DbColumnCommentParser(String comment) {

		// parse comment
		if (comment != null) {
			int index = comment.indexOf('@');
			if (index >= 0) {
				++index;
				int end = comment.indexOf('@', index);
				if (end > index) {
					String[] codeParts = comment.substring(index, end).split(",");
					for (String code: codeParts) {
						if (code.startsWith("name=")) {
							this.nameOverride = code.substring("name=".length());
						} else if (code.startsWith("ignore")) {
							this.ignoreOverride = true;
						} else if (code.startsWith("double")) {
							this.doubleOverride = true;
						} else if (code.startsWith("base")) {
							this.baseColumn = true;
						}
					}
				}
			}
		}
	}

	public String getNameOverride() {

		return nameOverride;
	}

	public boolean isIgnoreOverride() {

		return ignoreOverride;
	}

	public boolean isDoubleOverride() {

		return doubleOverride;
	}

	public boolean isBaseColumn() {

		return baseColumn;
	}
}
