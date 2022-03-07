package com.softicar.platform.core.module.user;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.core.module.user.rule.ip.AGUserAllowedIpRule;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUser for
 * database table <i>Core.User</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserGenerated extends AbstractDbObject<AGUser> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUser, AGUserGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "User", AGUser::new, AGUser.class);
	static {
		BUILDER.setTitle(CoreI18n.USER);
		BUILDER.setPluralTitle(CoreI18n.USERS);
	}

	public static final IDbIdField<AGUser> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGUser> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGUser> LOGIN_NAME = BUILDER.addStringField("loginName", o->o.m_loginName, (o,v)->o.m_loginName=v).setTitle(CoreI18n.LOGIN_NAME).setMaximumLength(255);
	public static final IDbStringField<AGUser> FIRST_NAME = BUILDER.addStringField("firstName", o->o.m_firstName, (o,v)->o.m_firstName=v).setTitle(CoreI18n.FIRST_NAME).setMaximumLength(255);
	public static final IDbStringField<AGUser> LAST_NAME = BUILDER.addStringField("lastName", o->o.m_lastName, (o,v)->o.m_lastName=v).setTitle(CoreI18n.LAST_NAME).setMaximumLength(255);
	public static final IDbStringField<AGUser> EMAIL_ADDRESS = BUILDER.addStringField("emailAddress", o->o.m_emailAddress, (o,v)->o.m_emailAddress=v).setTitle(CoreI18n.EMAIL_ADDRESS).setDefault("").setMaximumLength(255);
	public static final IDbBooleanField<AGUser> SYSTEM_USER = BUILDER.addBooleanField("systemUser", o->o.m_systemUser, (o,v)->o.m_systemUser=v).setTitle(CoreI18n.SYSTEM_USER).setDefault(false);
	public static final IDbForeignField<AGUser, AGCoreLanguage> PREFERRED_LANGUAGE = BUILDER.addForeignField("preferredLanguage", o->o.m_preferredLanguage, (o,v)->o.m_preferredLanguage=v, AGCoreLanguage.ID).setTitle(CoreI18n.PREFERRED_LANGUAGE).setNullable().setDefault(null);
	public static final IDbForeignField<AGUser, AGPasswordPolicy> PASSWORD_POLICY = BUILDER.addForeignField("passwordPolicy", o->o.m_passwordPolicy, (o,v)->o.m_passwordPolicy=v, AGPasswordPolicy.ID).setTitle(CoreI18n.PASSWORD_POLICY).setNullable().setDefault(null);
	public static final IDbForeignField<AGUser, AGUserAllowedIpRule> ALLOWED_IP_RULE = BUILDER.addForeignField("allowedIpRule", o->o.m_allowedIpRule, (o,v)->o.m_allowedIpRule=v, AGUserAllowedIpRule.ID).setTitle(CoreI18n.ALLOWED_IP_RULE).setNullable().setDefault(null);
	public static final IDbKey<AGUser> UK_LOGIN_NAME = BUILDER.addUniqueKey("loginName", LOGIN_NAME);
	public static final AGUserTable TABLE = new AGUserTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUser> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUser get(Integer id) {

		return TABLE.get(id);
	}

	public static AGUser loadByLoginName(String loginName) {

		return TABLE//
				.createSelect()
				.where(LOGIN_NAME.equal(loginName))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGUser setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getLoginName() {

		return getValue(LOGIN_NAME);
	}

	public final AGUser setLoginName(String value) {

		return setValue(LOGIN_NAME, value);
	}

	public final String getFirstName() {

		return getValue(FIRST_NAME);
	}

	public final AGUser setFirstName(String value) {

		return setValue(FIRST_NAME, value);
	}

	public final String getLastName() {

		return getValue(LAST_NAME);
	}

	public final AGUser setLastName(String value) {

		return setValue(LAST_NAME, value);
	}

	public final String getEmailAddress() {

		return getValue(EMAIL_ADDRESS);
	}

	public final AGUser setEmailAddress(String value) {

		return setValue(EMAIL_ADDRESS, value);
	}

	public final Boolean isSystemUser() {

		return getValue(SYSTEM_USER);
	}

	public final AGUser setSystemUser(Boolean value) {

		return setValue(SYSTEM_USER, value);
	}

	public final Integer getPreferredLanguageID() {

		return getValueId(PREFERRED_LANGUAGE);
	}

	public final AGCoreLanguage getPreferredLanguage() {

		return getValue(PREFERRED_LANGUAGE);
	}

	public final AGUser setPreferredLanguage(AGCoreLanguage value) {

		return setValue(PREFERRED_LANGUAGE, value);
	}

	public final Integer getPasswordPolicyID() {

		return getValueId(PASSWORD_POLICY);
	}

	public final AGPasswordPolicy getPasswordPolicy() {

		return getValue(PASSWORD_POLICY);
	}

	public final AGUser setPasswordPolicy(AGPasswordPolicy value) {

		return setValue(PASSWORD_POLICY, value);
	}

	public final Integer getAllowedIpRuleID() {

		return getValueId(ALLOWED_IP_RULE);
	}

	public final AGUserAllowedIpRule getAllowedIpRule() {

		return getValue(ALLOWED_IP_RULE);
	}

	public final AGUser setAllowedIpRule(AGUserAllowedIpRule value) {

		return setValue(ALLOWED_IP_RULE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUserTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_loginName;
	private String m_firstName;
	private String m_lastName;
	private String m_emailAddress;
	private Boolean m_systemUser;
	private AGCoreLanguage m_preferredLanguage;
	private AGPasswordPolicy m_passwordPolicy;
	private AGUserAllowedIpRule m_allowedIpRule;
}

