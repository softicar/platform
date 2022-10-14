package com.softicar.platform.core.module.server;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
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
 * This is the automatically generated class AGServerLog for
 * database table <i>Core.ServerLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGServerLog extends AbstractDbRecord<AGServerLog, Tuple2<AGServer, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGServerLog, AGServerLog, Tuple2<AGServer, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ServerLog", AGServerLog::new, AGServerLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SERVER_LOG);
		BUILDER.setPluralTitle(CoreI18n.SERVER_LOGS);
	}

	public static final IDbForeignField<AGServerLog, AGServer> SERVER = BUILDER.addForeignField("server", o->o.m_server, (o,v)->o.m_server=v, AGServer.ID).setTitle(CoreI18n.SERVER).setCascade(true, true).setForeignKeyName("ServerLog_ibfk_1");
	public static final IDbForeignField<AGServerLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("ServerLog_ibfk_2");
	public static final IDbBooleanField<AGServerLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGServerLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGServerLog> ADDRESS = BUILDER.addStringField("address", o->o.m_address, (o,v)->o.m_address=v).setTitle(CoreI18n.ADDRESS).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbIntegerField<AGServerLog> PORT = BUILDER.addIntegerField("port", o->o.m_port, (o,v)->o.m_port=v).setTitle(CoreI18n.PORT).setNullable().setDefault(null);
	public static final IDbStringField<AGServerLog> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGServerLog> PASSWORD = BUILDER.addStringField("password", o->o.m_password, (o,v)->o.m_password=v).setTitle(CoreI18n.PASSWORD).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGServerLog> DOMAIN = BUILDER.addStringField("domain", o->o.m_domain, (o,v)->o.m_domain=v).setTitle(CoreI18n.DOMAIN).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGServerLog, AGUuid> CONNECTOR_UUID = BUILDER.addForeignField("connectorUuid", o->o.m_connectorUuid, (o,v)->o.m_connectorUuid=v, AGUuid.ID).setTitle(CoreI18n.CONNECTOR_UUID).setNullable().setDefault(null).setCascade(true, true).setForeignKeyName("ServerLog_ibfk_3");
	public static final IDbStringField<AGServerLog> CONNECTOR_CONFIGURATION = BUILDER.addStringField("connectorConfiguration", o->o.m_connectorConfiguration, (o,v)->o.m_connectorConfiguration=v).setTitle(CoreI18n.CONNECTOR_CONFIGURATION).setNullable().setDefault(null).setLengthBits(24);
	public static final IDbTableKey<AGServerLog, Tuple2<AGServer, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SERVER, TRANSACTION));
	public static final IDbKey<AGServerLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGServerLog> IK_CONNECTOR_UUID = BUILDER.addIndexKey("connectorUuid", CONNECTOR_UUID);
	public static final DbRecordTable<AGServerLog, Tuple2<AGServer, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGServerLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getServerID() {

		return getValueId(SERVER);
	}

	public final AGServer getServer() {

		return getValue(SERVER);
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

	public final AGServerLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGServerLog setName(String value) {

		return setValue(NAME, value);
	}

	public final String getAddress() {

		return getValue(ADDRESS);
	}

	public final AGServerLog setAddress(String value) {

		return setValue(ADDRESS, value);
	}

	public final Integer getPort() {

		return getValue(PORT);
	}

	public final AGServerLog setPort(Integer value) {

		return setValue(PORT, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGServerLog setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getPassword() {

		return getValue(PASSWORD);
	}

	public final AGServerLog setPassword(String value) {

		return setValue(PASSWORD, value);
	}

	public final String getDomain() {

		return getValue(DOMAIN);
	}

	public final AGServerLog setDomain(String value) {

		return setValue(DOMAIN, value);
	}

	public final Integer getConnectorUuidID() {

		return getValueId(CONNECTOR_UUID);
	}

	public final AGUuid getConnectorUuid() {

		return getValue(CONNECTOR_UUID);
	}

	public final AGServerLog setConnectorUuid(AGUuid value) {

		return setValue(CONNECTOR_UUID, value);
	}

	public final String getConnectorConfiguration() {

		return getValue(CONNECTOR_CONFIGURATION);
	}

	public final AGServerLog setConnectorConfiguration(String value) {

		return setValue(CONNECTOR_CONFIGURATION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGServerLog, Tuple2<AGServer, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGServer m_server;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_name;
	private String m_address;
	private Integer m_port;
	private String m_username;
	private String m_password;
	private String m_domain;
	private AGUuid m_connectorUuid;
	private String m_connectorConfiguration;
}

