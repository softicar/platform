package com.softicar.platform.db.structure.table;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Enumerates existing types of database structure features.
 *
 * @author Alexander Schmidt
 */
public enum DbTableStructureFeatureType {

	COLUMN("Column"),
	ENUM_TABLE_ROW("Enum Table Row"),
	FOREIGN_KEY("Foreign Key"),
	KEY("Key"),
	TABLE("Table");

	private IDisplayString title;

	private DbTableStructureFeatureType(String title) {

		this.title = IDisplayString.create(title);
	}

	public IDisplayString getTitle() {

		return title;
	}
}
