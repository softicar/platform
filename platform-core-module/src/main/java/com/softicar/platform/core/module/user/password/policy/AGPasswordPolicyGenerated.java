package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGPasswordPolicy for
 * database table <i>Core.PasswordPolicy</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGPasswordPolicyGenerated extends AbstractDbObject<AGPasswordPolicy> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGPasswordPolicy, AGPasswordPolicyGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "PasswordPolicy", AGPasswordPolicy::new, AGPasswordPolicy.class);
	static {
		BUILDER.setTitle(CoreI18n.PASSWORD_POLICY);
		BUILDER.setPluralTitle(CoreI18n.PASSWORD_POLICIES);
	}

	public static final IDbIdField<AGPasswordPolicy> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGPasswordPolicy> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGPasswordPolicy> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbIntegerField<AGPasswordPolicy> MAXIMUM_PASSWORD_REUSE = BUILDER.addIntegerField("maximumPasswordReuse", o->o.m_maximumPasswordReuse, (o,v)->o.m_maximumPasswordReuse=v).setTitle(CoreI18n.MAXIMUM_PASSWORD_REUSE).setNullable().setDefault(null);
	public static final IDbIntegerField<AGPasswordPolicy> MAXIMUM_PASSWORD_AGE = BUILDER.addIntegerField("maximumPasswordAge", o->o.m_maximumPasswordAge, (o,v)->o.m_maximumPasswordAge=v).setTitle(CoreI18n.MAXIMUM_PASSWORD_AGE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGPasswordPolicy> TWO_FACTOR_AUTHENTICATION = BUILDER.addBooleanField("twoFactorAuthentication", o->o.m_twoFactorAuthentication, (o,v)->o.m_twoFactorAuthentication=v).setTitle(CoreI18n.TWO_FACTOR_AUTHENTICATION).setDefault(false);
	public static final AGPasswordPolicyTable TABLE = new AGPasswordPolicyTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGPasswordPolicy> createSelect() {

		return TABLE.createSelect();
	}

	public static AGPasswordPolicy get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGPasswordPolicy setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGPasswordPolicy setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getMaximumPasswordReuse() {

		return getValue(MAXIMUM_PASSWORD_REUSE);
	}

	public final AGPasswordPolicy setMaximumPasswordReuse(Integer value) {

		return setValue(MAXIMUM_PASSWORD_REUSE, value);
	}

	public final Integer getMaximumPasswordAge() {

		return getValue(MAXIMUM_PASSWORD_AGE);
	}

	public final AGPasswordPolicy setMaximumPasswordAge(Integer value) {

		return setValue(MAXIMUM_PASSWORD_AGE, value);
	}

	public final Boolean isTwoFactorAuthentication() {

		return getValue(TWO_FACTOR_AUTHENTICATION);
	}

	public final AGPasswordPolicy setTwoFactorAuthentication(Boolean value) {

		return setValue(TWO_FACTOR_AUTHENTICATION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGPasswordPolicyTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_name;
	private Integer m_maximumPasswordReuse;
	private Integer m_maximumPasswordAge;
	private Boolean m_twoFactorAuthentication;
}

