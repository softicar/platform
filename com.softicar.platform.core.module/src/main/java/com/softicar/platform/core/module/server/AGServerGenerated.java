package com.softicar.platform.core.module.server;

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
 * This is the automatically generated class AGServer for
 * database table <i>Core.Server</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGServerGenerated extends AbstractDbObject<AGServer> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGServer, AGServerGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Server", AGServer::new, AGServer.class);
	static {
		BUILDER.setTitle(CoreI18n.SERVER);
		BUILDER.setPluralTitle(CoreI18n.SERVERS);
	}

	public static final IDbIdField<AGServer> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGServer> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGServer> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbStringField<AGServer> ADDRESS = BUILDER.addStringField("address", o->o.m_address, (o,v)->o.m_address=v).setTitle(CoreI18n.ADDRESS).setMaximumLength(255);
	public static final IDbIntegerField<AGServer> PORT = BUILDER.addIntegerField("port", o->o.m_port, (o,v)->o.m_port=v).setTitle(CoreI18n.PORT);
	public static final IDbStringField<AGServer> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGServer> PASSWORD = BUILDER.addStringField("password", o->o.m_password, (o,v)->o.m_password=v).setTitle(CoreI18n.PASSWORD).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGServer> DOMAIN = BUILDER.addStringField("domain", o->o.m_domain, (o,v)->o.m_domain=v).setTitle(CoreI18n.DOMAIN).setNullable().setDefault(null).setMaximumLength(255);
	public static final AGServerTable TABLE = new AGServerTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGServer> createSelect() {

		return TABLE.createSelect();
	}

	public static AGServer get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGServer setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGServer setName(String value) {

		return setValue(NAME, value);
	}

	public final String getAddress() {

		return getValue(ADDRESS);
	}

	public final AGServer setAddress(String value) {

		return setValue(ADDRESS, value);
	}

	public final Integer getPort() {

		return getValue(PORT);
	}

	public final AGServer setPort(Integer value) {

		return setValue(PORT, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGServer setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getPassword() {

		return getValue(PASSWORD);
	}

	public final AGServer setPassword(String value) {

		return setValue(PASSWORD, value);
	}

	public final String getDomain() {

		return getValue(DOMAIN);
	}

	public final AGServer setDomain(String value) {

		return setValue(DOMAIN, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGServerTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_name;
	private String m_address;
	private Integer m_port;
	private String m_username;
	private String m_password;
	private String m_domain;
}

