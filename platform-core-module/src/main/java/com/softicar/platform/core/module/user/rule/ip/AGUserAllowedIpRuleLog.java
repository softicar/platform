package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGUserAllowedIpRuleLog for
 * database table <i>Core.UserAllowedIpRuleLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserAllowedIpRuleLog extends AbstractDbRecord<AGUserAllowedIpRuleLog, Tuple2<AGUserAllowedIpRule, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGUserAllowedIpRuleLog, AGUserAllowedIpRuleLog, Tuple2<AGUserAllowedIpRule, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "UserAllowedIpRuleLog", AGUserAllowedIpRuleLog::new, AGUserAllowedIpRuleLog.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_ALLOWED_IP_RULE_LOG);
		BUILDER.setPluralTitle(CoreI18n.USER_ALLOWED_IP_RULE_LOGS);
	}

	public static final IDbForeignField<AGUserAllowedIpRuleLog, AGUserAllowedIpRule> USER_ALLOWED_IP_RULE = BUILDER.addForeignField("userAllowedIpRule", o->o.m_userAllowedIpRule, (o,v)->o.m_userAllowedIpRule=v, AGUserAllowedIpRule.ID).setTitle(CoreI18n.USER_ALLOWED_IP_RULE).setCascade(true, true).setForeignKeyName("UserAllowedIpRuleLog_ibfk_1");
	public static final IDbForeignField<AGUserAllowedIpRuleLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("UserAllowedIpRuleLog_ibfk_2");
	public static final IDbBooleanField<AGUserAllowedIpRuleLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGUserAllowedIpRuleLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGUserAllowedIpRuleLog> ADDRESSES = BUILDER.addStringField("addresses", o->o.m_addresses, (o,v)->o.m_addresses=v).setTitle(CoreI18n.ADDRESSES).setNullable().setDefault(null).setLengthBits(16);
	public static final IDbTableKey<AGUserAllowedIpRuleLog, Tuple2<AGUserAllowedIpRule, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER_ALLOWED_IP_RULE, TRANSACTION));
	public static final IDbKey<AGUserAllowedIpRuleLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGUserAllowedIpRuleLog, Tuple2<AGUserAllowedIpRule, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGUserAllowedIpRuleLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserAllowedIpRuleID() {

		return getValueId(USER_ALLOWED_IP_RULE);
	}

	public final AGUserAllowedIpRule getUserAllowedIpRule() {

		return getValue(USER_ALLOWED_IP_RULE);
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

	public final AGUserAllowedIpRuleLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGUserAllowedIpRuleLog setName(String value) {

		return setValue(NAME, value);
	}

	public final String getAddresses() {

		return getValue(ADDRESSES);
	}

	public final AGUserAllowedIpRuleLog setAddresses(String value) {

		return setValue(ADDRESSES, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGUserAllowedIpRuleLog, Tuple2<AGUserAllowedIpRule, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUserAllowedIpRule m_userAllowedIpRule;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_name;
	private String m_addresses;
}

