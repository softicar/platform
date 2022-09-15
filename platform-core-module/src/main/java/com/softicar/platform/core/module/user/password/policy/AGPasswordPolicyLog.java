package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGPasswordPolicyLog for
 * database table <i>Core.PasswordPolicyLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGPasswordPolicyLog extends AbstractDbRecord<AGPasswordPolicyLog, Tuple2<AGPasswordPolicy, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGPasswordPolicyLog, AGPasswordPolicyLog, Tuple2<AGPasswordPolicy, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "PasswordPolicyLog", AGPasswordPolicyLog::new, AGPasswordPolicyLog.class);
	static {
		BUILDER.setTitle(CoreI18n.PASSWORD_POLICY_LOG);
		BUILDER.setPluralTitle(CoreI18n.PASSWORD_POLICY_LOGS);
	}

	public static final IDbForeignField<AGPasswordPolicyLog, AGPasswordPolicy> PASSWORD_POLICY = BUILDER.addForeignField("passwordPolicy", o->o.m_passwordPolicy, (o,v)->o.m_passwordPolicy=v, AGPasswordPolicy.ID).setTitle(CoreI18n.PASSWORD_POLICY).setCascade(true, true).setForeignKeyName("PasswordPolicyLog_ibfk_1");
	public static final IDbForeignField<AGPasswordPolicyLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("PasswordPolicyLog_ibfk_2");
	public static final IDbBooleanField<AGPasswordPolicyLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGPasswordPolicyLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbIntegerField<AGPasswordPolicyLog> MAXIMUM_PASSWORD_REUSE = BUILDER.addIntegerField("maximumPasswordReuse", o->o.m_maximumPasswordReuse, (o,v)->o.m_maximumPasswordReuse=v).setTitle(CoreI18n.MAXIMUM_PASSWORD_REUSE).setNullable().setDefault(null);
	public static final IDbIntegerField<AGPasswordPolicyLog> MAXIMUM_PASSWORD_AGE = BUILDER.addIntegerField("maximumPasswordAge", o->o.m_maximumPasswordAge, (o,v)->o.m_maximumPasswordAge=v).setTitle(CoreI18n.MAXIMUM_PASSWORD_AGE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGPasswordPolicyLog> TWO_FACTOR_AUTHENTICATION = BUILDER.addBooleanField("twoFactorAuthentication", o->o.m_twoFactorAuthentication, (o,v)->o.m_twoFactorAuthentication=v).setTitle(CoreI18n.TWO_FACTOR_AUTHENTICATION).setNullable().setDefault(null);
	public static final IDbTableKey<AGPasswordPolicyLog, Tuple2<AGPasswordPolicy, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(PASSWORD_POLICY, TRANSACTION));
	public static final IDbKey<AGPasswordPolicyLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGPasswordPolicyLog, Tuple2<AGPasswordPolicy, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGPasswordPolicyLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getPasswordPolicyID() {

		return getValueId(PASSWORD_POLICY);
	}

	public final AGPasswordPolicy getPasswordPolicy() {

		return getValue(PASSWORD_POLICY);
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

	public final AGPasswordPolicyLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGPasswordPolicyLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getMaximumPasswordReuse() {

		return getValue(MAXIMUM_PASSWORD_REUSE);
	}

	public final AGPasswordPolicyLog setMaximumPasswordReuse(Integer value) {

		return setValue(MAXIMUM_PASSWORD_REUSE, value);
	}

	public final Integer getMaximumPasswordAge() {

		return getValue(MAXIMUM_PASSWORD_AGE);
	}

	public final AGPasswordPolicyLog setMaximumPasswordAge(Integer value) {

		return setValue(MAXIMUM_PASSWORD_AGE, value);
	}

	public final Boolean isTwoFactorAuthentication() {

		return getValue(TWO_FACTOR_AUTHENTICATION);
	}

	public final AGPasswordPolicyLog setTwoFactorAuthentication(Boolean value) {

		return setValue(TWO_FACTOR_AUTHENTICATION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGPasswordPolicyLog, Tuple2<AGPasswordPolicy, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGPasswordPolicy m_passwordPolicy;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_name;
	private Integer m_maximumPasswordReuse;
	private Integer m_maximumPasswordAge;
	private Boolean m_twoFactorAuthentication;
}

