package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.algorithm.AGUserPasswordAlgorithm;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserPassword for
 * database table <i>Core.UserPassword</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserPasswordGenerated extends AbstractDbObject<AGUserPassword> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserPassword, AGUserPasswordGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "UserPassword", AGUserPassword::new, AGUserPassword.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_PASSWORD);
		BUILDER.setPluralTitle(CoreI18n.USER_PASSWORDS);
	}

	public static final IDbIdField<AGUserPassword> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserPassword, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setForeignKeyName("UserPassword_ibfk_1");
	public static final IDbBooleanField<AGUserPassword> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGUserPassword, AGUserPasswordAlgorithm> ALGORITHM = BUILDER.addForeignField("algorithm", o->o.m_algorithm, (o,v)->o.m_algorithm=v, AGUserPasswordAlgorithm.ID).setTitle(CoreI18n.ALGORITHM).setNullable().setDefault(null).setForeignKeyName("UserPassword_ibfk_2");
	public static final IDbStringField<AGUserPassword> ENCRYPTED_PASSWORD = BUILDER.addStringField("encryptedPassword", o->o.m_encryptedPassword, (o,v)->o.m_encryptedPassword=v).setTitle(CoreI18n.ENCRYPTED_PASSWORD).setLengthBits(16);
	public static final IDbDayTimeField<AGUserPassword> CREATED_AT = BUILDER.addDayTimeField("createdAt", o->o.m_createdAt, (o,v)->o.m_createdAt=v).setTitle(CoreI18n.CREATED_AT).setDefaultNow();
	public static final IDbBooleanField<AGUserPassword> POLICY_FULFILLED = BUILDER.addBooleanField("policyFulfilled", o->o.m_policyFulfilled, (o,v)->o.m_policyFulfilled=v).setTitle(CoreI18n.POLICY_FULFILLED).setDefault(true);
	public static final IDbBooleanField<AGUserPassword> COMPROMISED = BUILDER.addBooleanField("compromised", o->o.m_compromised, (o,v)->o.m_compromised=v).setTitle(CoreI18n.COMPROMISED).setDefault(false);
	public static final IDbDayTimeField<AGUserPassword> LAST_LOGIN_AT = BUILDER.addDayTimeField("lastLoginAt", o->o.m_lastLoginAt, (o,v)->o.m_lastLoginAt=v).setTitle(CoreI18n.LAST_LOGIN_AT).setDefault(new DayTime(Day.get(719528), 0, 0, 0));
	public static final IDbKey<AGUserPassword> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final IDbKey<AGUserPassword> IK_ALGORITHM = BUILDER.addIndexKey("algorithm", ALGORITHM);
	public static final AGUserPasswordTable TABLE = new AGUserPasswordTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserPassword> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserPassword get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGUserPassword setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGUserPassword setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getAlgorithmID() {

		return getValueId(ALGORITHM);
	}

	public final AGUserPasswordAlgorithm getAlgorithm() {

		return getValue(ALGORITHM);
	}

	public final AGUserPassword setAlgorithm(AGUserPasswordAlgorithm value) {

		return setValue(ALGORITHM, value);
	}

	public final String getEncryptedPassword() {

		return getValue(ENCRYPTED_PASSWORD);
	}

	public final AGUserPassword setEncryptedPassword(String value) {

		return setValue(ENCRYPTED_PASSWORD, value);
	}

	public final DayTime getCreatedAt() {

		return getValue(CREATED_AT);
	}

	public final AGUserPassword setCreatedAt(DayTime value) {

		return setValue(CREATED_AT, value);
	}

	public final Boolean isPolicyFulfilled() {

		return getValue(POLICY_FULFILLED);
	}

	public final AGUserPassword setPolicyFulfilled(Boolean value) {

		return setValue(POLICY_FULFILLED, value);
	}

	public final Boolean isCompromised() {

		return getValue(COMPROMISED);
	}

	public final AGUserPassword setCompromised(Boolean value) {

		return setValue(COMPROMISED, value);
	}

	public final DayTime getLastLoginAt() {

		return getValue(LAST_LOGIN_AT);
	}

	public final AGUserPassword setLastLoginAt(DayTime value) {

		return setValue(LAST_LOGIN_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUserPasswordTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private Boolean m_active;
	private AGUserPasswordAlgorithm m_algorithm;
	private String m_encryptedPassword;
	private DayTime m_createdAt;
	private Boolean m_policyFulfilled;
	private Boolean m_compromised;
	private DayTime m_lastLoginAt;
}

