package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserAllowedIpRule for
 * database table <i>Core.UserAllowedIpRule</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserAllowedIpRuleGenerated extends AbstractDbObject<AGUserAllowedIpRule> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserAllowedIpRule, AGUserAllowedIpRuleGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "UserAllowedIpRule", AGUserAllowedIpRule::new, AGUserAllowedIpRule.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_ALLOWED_IP_RULE);
		BUILDER.setPluralTitle(CoreI18n.USER_ALLOWED_IP_RULES);
	}

	public static final IDbIdField<AGUserAllowedIpRule> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGUserAllowedIpRule> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGUserAllowedIpRule> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbStringField<AGUserAllowedIpRule> ADDRESSES = BUILDER.addStringField("addresses", o->o.m_addresses, (o,v)->o.m_addresses=v).setTitle(CoreI18n.ADDRESSES).setLengthBits(16).setComment("each line contains an ip or network address");
	public static final IDbKey<AGUserAllowedIpRule> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final AGUserAllowedIpRuleTable TABLE = new AGUserAllowedIpRuleTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserAllowedIpRule> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserAllowedIpRule get(Integer id) {

		return TABLE.get(id);
	}

	public static AGUserAllowedIpRule loadByName(String name) {

		return TABLE//
				.createSelect()
				.where(NAME.isEqual(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGUserAllowedIpRule setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGUserAllowedIpRule setName(String value) {

		return setValue(NAME, value);
	}

	public final String getAddresses() {

		return getValue(ADDRESSES);
	}

	public final AGUserAllowedIpRule setAddresses(String value) {

		return setValue(ADDRESSES, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUserAllowedIpRuleTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_name;
	private String m_addresses;
}

