package com.softicar.platform.core.module.user;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.core.module.user.rule.ip.AGUserAllowedIpRule;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGUserLog for
 * database table <i>Core.UserLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserLog extends AbstractDbRecord<AGUserLog, Tuple2<AGUser, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGUserLog, AGUserLog, Tuple2<AGUser, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "UserLog", AGUserLog::new, AGUserLog.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_LOG);
		BUILDER.setPluralTitle(CoreI18n.USER_LOGS);
	}

	public static final IDbForeignField<AGUserLog, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGUserLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbBooleanField<AGUserLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGUserLog> LOGIN_NAME = BUILDER.addStringField("loginName", o->o.m_loginName, (o,v)->o.m_loginName=v).setTitle(CoreI18n.LOGIN_NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGUserLog> FIRST_NAME = BUILDER.addStringField("firstName", o->o.m_firstName, (o,v)->o.m_firstName=v).setTitle(CoreI18n.FIRST_NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGUserLog> LAST_NAME = BUILDER.addStringField("lastName", o->o.m_lastName, (o,v)->o.m_lastName=v).setTitle(CoreI18n.LAST_NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGUserLog> EMAIL_ADDRESS = BUILDER.addStringField("emailAddress", o->o.m_emailAddress, (o,v)->o.m_emailAddress=v).setTitle(CoreI18n.EMAIL_ADDRESS).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGUserLog, AGLocalization> LOCALIZATION = BUILDER.addForeignField("localization", o->o.m_localization, (o,v)->o.m_localization=v, AGLocalization.ID).setTitle(CoreI18n.LOCALIZATION).setNullable().setDefault(null);
	public static final IDbBooleanField<AGUserLog> COLLAPSE_NAVIGATION_FOLDERS = BUILDER.addBooleanField("collapseNavigationFolders", o->o.m_collapseNavigationFolders, (o,v)->o.m_collapseNavigationFolders=v).setTitle(CoreI18n.COLLAPSE_NAVIGATION_FOLDERS).setNullable().setDefault(null);
	public static final IDbForeignField<AGUserLog, AGPasswordPolicy> PASSWORD_POLICY = BUILDER.addForeignField("passwordPolicy", o->o.m_passwordPolicy, (o,v)->o.m_passwordPolicy=v, AGPasswordPolicy.ID).setTitle(CoreI18n.PASSWORD_POLICY).setNullable().setDefault(null);
	public static final IDbForeignField<AGUserLog, AGUserAllowedIpRule> ALLOWED_IP_RULE = BUILDER.addForeignField("allowedIpRule", o->o.m_allowedIpRule, (o,v)->o.m_allowedIpRule=v, AGUserAllowedIpRule.ID).setTitle(CoreI18n.ALLOWED_IP_RULE).setNullable().setDefault(null);
	public static final IDbTableKey<AGUserLog, Tuple2<AGUser, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER, TRANSACTION));
	public static final DbRecordTable<AGUserLog, Tuple2<AGUser, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGUserLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGUserLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getLoginName() {

		return getValue(LOGIN_NAME);
	}

	public final AGUserLog setLoginName(String value) {

		return setValue(LOGIN_NAME, value);
	}

	public final String getFirstName() {

		return getValue(FIRST_NAME);
	}

	public final AGUserLog setFirstName(String value) {

		return setValue(FIRST_NAME, value);
	}

	public final String getLastName() {

		return getValue(LAST_NAME);
	}

	public final AGUserLog setLastName(String value) {

		return setValue(LAST_NAME, value);
	}

	public final String getEmailAddress() {

		return getValue(EMAIL_ADDRESS);
	}

	public final AGUserLog setEmailAddress(String value) {

		return setValue(EMAIL_ADDRESS, value);
	}

	public final Integer getLocalizationID() {

		return getValueId(LOCALIZATION);
	}

	public final AGLocalization getLocalization() {

		return getValue(LOCALIZATION);
	}

	public final AGUserLog setLocalization(AGLocalization value) {

		return setValue(LOCALIZATION, value);
	}

	public final Boolean isCollapseNavigationFolders() {

		return getValue(COLLAPSE_NAVIGATION_FOLDERS);
	}

	public final AGUserLog setCollapseNavigationFolders(Boolean value) {

		return setValue(COLLAPSE_NAVIGATION_FOLDERS, value);
	}

	public final Integer getPasswordPolicyID() {

		return getValueId(PASSWORD_POLICY);
	}

	public final AGPasswordPolicy getPasswordPolicy() {

		return getValue(PASSWORD_POLICY);
	}

	public final AGUserLog setPasswordPolicy(AGPasswordPolicy value) {

		return setValue(PASSWORD_POLICY, value);
	}

	public final Integer getAllowedIpRuleID() {

		return getValueId(ALLOWED_IP_RULE);
	}

	public final AGUserAllowedIpRule getAllowedIpRule() {

		return getValue(ALLOWED_IP_RULE);
	}

	public final AGUserLog setAllowedIpRule(AGUserAllowedIpRule value) {

		return setValue(ALLOWED_IP_RULE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGUserLog, Tuple2<AGUser, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUser m_user;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_loginName;
	private String m_firstName;
	private String m_lastName;
	private String m_emailAddress;
	private AGLocalization m_localization;
	private Boolean m_collapseNavigationFolders;
	private AGPasswordPolicy m_passwordPolicy;
	private AGUserAllowedIpRule m_allowedIpRule;
}

