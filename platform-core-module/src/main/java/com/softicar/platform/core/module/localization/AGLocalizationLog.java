package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGLocalizationLog for
 * database table <i>Core.LocalizationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLocalizationLog extends AbstractDbRecord<AGLocalizationLog, Tuple2<AGLocalization, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGLocalizationLog, AGLocalizationLog, Tuple2<AGLocalization, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "LocalizationLog", AGLocalizationLog::new, AGLocalizationLog.class);
	static {
		BUILDER.setTitle(CoreI18n.LOCALIZATION_LOG);
		BUILDER.setPluralTitle(CoreI18n.LOCALIZATION_LOGS);
	}

	public static final IDbForeignField<AGLocalizationLog, AGLocalization> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGLocalization.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGLocalizationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbStringField<AGLocalizationLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGLocalizationLog, AGCoreLanguage> LANGUAGE = BUILDER.addForeignField("language", o->o.m_language, (o,v)->o.m_language=v, AGCoreLanguage.ID).setTitle(CoreI18n.LANGUAGE).setNullable().setDefault(null);
	public static final IDbStringField<AGLocalizationLog> DECIMAL_SEPARATOR = BUILDER.addStringField("decimalSeparator", o->o.m_decimalSeparator, (o,v)->o.m_decimalSeparator=v).setTitle(CoreI18n.DECIMAL_SEPARATOR).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGLocalizationLog> DIGIT_GROUP_SEPARATOR = BUILDER.addStringField("digitGroupSeparator", o->o.m_digitGroupSeparator, (o,v)->o.m_digitGroupSeparator=v).setTitle(CoreI18n.DIGIT_GROUP_SEPARATOR).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbTableKey<AGLocalizationLog, Tuple2<AGLocalization, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER, TRANSACTION));
	public static final DbRecordTable<AGLocalizationLog, Tuple2<AGLocalization, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGLocalizationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGLocalization getUser() {

		return getValue(USER);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGLocalizationLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getLanguageID() {

		return getValueId(LANGUAGE);
	}

	public final AGCoreLanguage getLanguage() {

		return getValue(LANGUAGE);
	}

	public final AGLocalizationLog setLanguage(AGCoreLanguage value) {

		return setValue(LANGUAGE, value);
	}

	public final String getDecimalSeparator() {

		return getValue(DECIMAL_SEPARATOR);
	}

	public final AGLocalizationLog setDecimalSeparator(String value) {

		return setValue(DECIMAL_SEPARATOR, value);
	}

	public final String getDigitGroupSeparator() {

		return getValue(DIGIT_GROUP_SEPARATOR);
	}

	public final AGLocalizationLog setDigitGroupSeparator(String value) {

		return setValue(DIGIT_GROUP_SEPARATOR, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGLocalizationLog, Tuple2<AGLocalization, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGLocalization m_user;
	private AGTransaction m_transaction;
	private String m_name;
	private AGCoreLanguage m_language;
	private String m_decimalSeparator;
	private String m_digitGroupSeparator;
}

