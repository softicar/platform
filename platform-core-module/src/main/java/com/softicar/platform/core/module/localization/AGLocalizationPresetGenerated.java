package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGLocalizationPreset for
 * database table <i>Core.LocalizationPreset</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLocalizationPresetGenerated extends AbstractDbObject<AGLocalizationPreset> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGLocalizationPreset, AGLocalizationPresetGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "LocalizationPreset", AGLocalizationPreset::new, AGLocalizationPreset.class);
	static {
		BUILDER.setTitle(CoreI18n.LOCALIZATION_PRESET);
		BUILDER.setPluralTitle(CoreI18n.LOCALIZATION_PRESETS);
	}

	public static final IDbIdField<AGLocalizationPreset> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGLocalizationPreset> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbForeignField<AGLocalizationPreset, AGCoreLanguage> LANGUAGE = BUILDER.addForeignField("language", o->o.m_language, (o,v)->o.m_language=v, AGCoreLanguage.ID).setTitle(CoreI18n.LANGUAGE);
	public static final IDbStringField<AGLocalizationPreset> DECIMAL_SEPARATOR = BUILDER.addStringField("decimalSeparator", o->o.m_decimalSeparator, (o,v)->o.m_decimalSeparator=v).setTitle(CoreI18n.DECIMAL_SEPARATOR).setMaximumLength(255);
	public static final IDbStringField<AGLocalizationPreset> DIGIT_GROUP_SEPARATOR = BUILDER.addStringField("digitGroupSeparator", o->o.m_digitGroupSeparator, (o,v)->o.m_digitGroupSeparator=v).setTitle(CoreI18n.DIGIT_GROUP_SEPARATOR).setMaximumLength(255);
	public static final IDbKey<AGLocalizationPreset> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final AGLocalizationPresetTable TABLE = new AGLocalizationPresetTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGLocalizationPreset> createSelect() {

		return TABLE.createSelect();
	}

	public static AGLocalizationPreset get(Integer id) {

		return TABLE.get(id);
	}

	public static AGLocalizationPreset loadByName(String name) {

		return TABLE//
				.createSelect()
				.where(NAME.equal(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGLocalizationPreset setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getLanguageID() {

		return getValueId(LANGUAGE);
	}

	public final AGCoreLanguage getLanguage() {

		return getValue(LANGUAGE);
	}

	public final AGLocalizationPreset setLanguage(AGCoreLanguage value) {

		return setValue(LANGUAGE, value);
	}

	public final String getDecimalSeparator() {

		return getValue(DECIMAL_SEPARATOR);
	}

	public final AGLocalizationPreset setDecimalSeparator(String value) {

		return setValue(DECIMAL_SEPARATOR, value);
	}

	public final String getDigitGroupSeparator() {

		return getValue(DIGIT_GROUP_SEPARATOR);
	}

	public final AGLocalizationPreset setDigitGroupSeparator(String value) {

		return setValue(DIGIT_GROUP_SEPARATOR, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGLocalizationPresetTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
	private AGCoreLanguage m_language;
	private String m_decimalSeparator;
	private String m_digitGroupSeparator;
}

