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
 * This is the automatically generated class AGLocalization for
 * database table <i>Core.Localization</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLocalizationGenerated extends AbstractDbObject<AGLocalization> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGLocalization, AGLocalizationGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Localization", AGLocalization::new, AGLocalization.class);
	static {
		BUILDER.setTitle(CoreI18n.LOCALIZATION);
		BUILDER.setPluralTitle(CoreI18n.LOCALIZATIONS);
	}

	public static final IDbIdField<AGLocalization> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGLocalization> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbForeignField<AGLocalization, AGCoreLanguage> LANGUAGE = BUILDER.addForeignField("language", o->o.m_language, (o,v)->o.m_language=v, AGCoreLanguage.ID).setTitle(CoreI18n.LANGUAGE);
	public static final IDbStringField<AGLocalization> DECIMAL_SEPARATOR = BUILDER.addStringField("decimalSeparator", o->o.m_decimalSeparator, (o,v)->o.m_decimalSeparator=v).setTitle(CoreI18n.DECIMAL_SEPARATOR).setMaximumLength(255);
	public static final IDbStringField<AGLocalization> DIGIT_GROUP_SEPARATOR = BUILDER.addStringField("digitGroupSeparator", o->o.m_digitGroupSeparator, (o,v)->o.m_digitGroupSeparator=v).setTitle(CoreI18n.DIGIT_GROUP_SEPARATOR).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGLocalization> DATE_FORMAT = BUILDER.addStringField("dateFormat", o->o.m_dateFormat, (o,v)->o.m_dateFormat=v).setTitle(CoreI18n.DATE_FORMAT).setMaximumLength(255);
	public static final IDbKey<AGLocalization> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final AGLocalizationTable TABLE = new AGLocalizationTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGLocalization> createSelect() {

		return TABLE.createSelect();
	}

	public static AGLocalization get(Integer id) {

		return TABLE.get(id);
	}

	public static AGLocalization loadByName(String name) {

		return TABLE//
				.createSelect()
				.where(NAME.equal(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGLocalization setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getLanguageID() {

		return getValueId(LANGUAGE);
	}

	public final AGCoreLanguage getLanguage() {

		return getValue(LANGUAGE);
	}

	public final AGLocalization setLanguage(AGCoreLanguage value) {

		return setValue(LANGUAGE, value);
	}

	public final String getDecimalSeparator() {

		return getValue(DECIMAL_SEPARATOR);
	}

	public final AGLocalization setDecimalSeparator(String value) {

		return setValue(DECIMAL_SEPARATOR, value);
	}

	public final String getDigitGroupSeparator() {

		return getValue(DIGIT_GROUP_SEPARATOR);
	}

	public final AGLocalization setDigitGroupSeparator(String value) {

		return setValue(DIGIT_GROUP_SEPARATOR, value);
	}

	public final String getDateFormat() {

		return getValue(DATE_FORMAT);
	}

	public final AGLocalization setDateFormat(String value) {

		return setValue(DATE_FORMAT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGLocalizationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
	private AGCoreLanguage m_language;
	private String m_decimalSeparator;
	private String m_digitGroupSeparator;
	private String m_dateFormat;
}

