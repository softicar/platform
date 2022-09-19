package com.softicar.platform.core.module.language;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGCoreLanguageEnum implements IDbEnumTableRowEnum<AGCoreLanguageEnum, AGCoreLanguage>, IDisplayable {

	ENGLISH(1, "English", "en"),
	GERMAN(2, "German", "de"),
	//
	;

	private Integer id;
	private String name;
	private String iso6391;

	private AGCoreLanguageEnum(Integer id, String name, String iso6391) {

		this.id = id;
		this.name = name;
		this.iso6391 = iso6391;
	}

	@Override
	public DbEnumTable<AGCoreLanguage, AGCoreLanguageEnum> getTable() {

		return AGCoreLanguage.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public IDisplayString toDisplay() {

		switch (this) {
		case ENGLISH:
			return CoreI18n.ENGLISH;
		case GERMAN:
			return CoreI18n.GERMAN;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGCoreLanguage> consumer) {

		consumer.consumeFieldValue(AGCoreLanguage.ID, id);
		consumer.consumeFieldValue(AGCoreLanguage.NAME, name);
		consumer.consumeFieldValue(AGCoreLanguage.ISO_6391, iso6391);
	}
}

