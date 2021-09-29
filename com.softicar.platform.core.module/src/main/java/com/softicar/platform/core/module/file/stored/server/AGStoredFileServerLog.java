package com.softicar.platform.core.module.file.stored.server;

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
 * This is the automatically generated class AGStoredFileServerLog for
 * database table <i>Core.StoredFileServerLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileServerLog extends AbstractDbRecord<AGStoredFileServerLog, Tuple2<AGStoredFileServer, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGStoredFileServerLog, AGStoredFileServerLog, Tuple2<AGStoredFileServer, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "StoredFileServerLog", AGStoredFileServerLog::new, AGStoredFileServerLog.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_SERVER_LOG);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_SERVER_LOGS);
	}

	public static final IDbForeignField<AGStoredFileServerLog, AGStoredFileServer> STORED_FILE_SERVER = BUILDER.addForeignField("storedFileServer", o->o.m_storedFileServer, (o,v)->o.m_storedFileServer=v, AGStoredFileServer.ID).setTitle(CoreI18n.STORED_FILE_SERVER);
	public static final IDbForeignField<AGStoredFileServerLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbBooleanField<AGStoredFileServerLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGStoredFileServerLog> URL = BUILDER.addStringField("url", o->o.m_url, (o,v)->o.m_url=v).setTitle(CoreI18n.URL).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileServerLog> DOMAIN = BUILDER.addStringField("domain", o->o.m_domain, (o,v)->o.m_domain=v).setTitle(CoreI18n.DOMAIN).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileServerLog> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileServerLog> PASSWORD = BUILDER.addStringField("password", o->o.m_password, (o,v)->o.m_password=v).setTitle(CoreI18n.PASSWORD).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbTableKey<AGStoredFileServerLog, Tuple2<AGStoredFileServer, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(STORED_FILE_SERVER, TRANSACTION));
	public static final IDbKey<AGStoredFileServerLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGStoredFileServerLog, Tuple2<AGStoredFileServer, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGStoredFileServerLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getStoredFileServerID() {

		return getValueId(STORED_FILE_SERVER);
	}

	public final AGStoredFileServer getStoredFileServer() {

		return getValue(STORED_FILE_SERVER);
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

	public final AGStoredFileServerLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getUrl() {

		return getValue(URL);
	}

	public final AGStoredFileServerLog setUrl(String value) {

		return setValue(URL, value);
	}

	public final String getDomain() {

		return getValue(DOMAIN);
	}

	public final AGStoredFileServerLog setDomain(String value) {

		return setValue(DOMAIN, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGStoredFileServerLog setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getPassword() {

		return getValue(PASSWORD);
	}

	public final AGStoredFileServerLog setPassword(String value) {

		return setValue(PASSWORD, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGStoredFileServerLog, Tuple2<AGStoredFileServer, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGStoredFileServer m_storedFileServer;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_url;
	private String m_domain;
	private String m_username;
	private String m_password;
}

