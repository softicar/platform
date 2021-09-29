package com.softicar.platform.core.module.language;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGCoreLanguage for
 * database table <i>Core.CoreLanguage</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGCoreLanguageGenerated extends AbstractDbEnumTableRow<AGCoreLanguage, AGCoreLanguageEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGCoreLanguage, AGCoreLanguageGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "CoreLanguage", AGCoreLanguage::new, AGCoreLanguage.class);
	static {
		BUILDER.setTitle(CoreI18n.CORE_LANGUAGE);
		BUILDER.setPluralTitle(CoreI18n.CORE_LANGUAGES);
	}

	public static final IDbIdField<AGCoreLanguage> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGCoreLanguage> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbStringField<AGCoreLanguage> ISO_6391 = BUILDER.addStringField("iso6391", o->o.m_iso6391, (o,v)->o.m_iso6391=v).setTitle(CoreI18n.ISO_6391).setMaximumLength(2);
	public static final IDbKey<AGCoreLanguage> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final IDbKey<AGCoreLanguage> UK_ISO_6391 = BUILDER.addUniqueKey("iso6391", ISO_6391);
	public static final DbEnumTable<AGCoreLanguage, AGCoreLanguageEnum> TABLE = new DbEnumTable<>(BUILDER, AGCoreLanguageEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGCoreLanguage setName(String value) {

		return setValue(NAME, value);
	}

	public final String getIso6391() {

		return getValue(ISO_6391);
	}

	public final AGCoreLanguage setIso6391(String value) {

		return setValue(ISO_6391, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGCoreLanguage, AGCoreLanguageEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
	private String m_iso6391;
}

