package com.softicar.platform.core.module.file.stored.repository;

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
 * This is the automatically generated class AGStoredFileRepositoryLog for
 * database table <i>Core.StoredFileRepositoryLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileRepositoryLog extends AbstractDbRecord<AGStoredFileRepositoryLog, Tuple2<AGStoredFileRepository, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGStoredFileRepositoryLog, AGStoredFileRepositoryLog, Tuple2<AGStoredFileRepository, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "StoredFileRepositoryLog", AGStoredFileRepositoryLog::new, AGStoredFileRepositoryLog.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_REPOSITORY_LOG);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_REPOSITORY_LOGS);
	}

	public static final IDbForeignField<AGStoredFileRepositoryLog, AGStoredFileRepository> STORED_FILE_REPOSITORY = BUILDER.addForeignField("storedFileRepository", o->o.m_storedFileRepository, (o,v)->o.m_storedFileRepository=v, AGStoredFileRepository.ID).setTitle(CoreI18n.STORED_FILE_REPOSITORY).setCascade(true, true).setForeignKeyName("StoredFileRepositoryLog_ibfk_1");
	public static final IDbForeignField<AGStoredFileRepositoryLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("StoredFileRepositoryLog_ibfk_2");
	public static final IDbBooleanField<AGStoredFileRepositoryLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGStoredFileRepositoryLog> URL = BUILDER.addStringField("url", o->o.m_url, (o,v)->o.m_url=v).setTitle(CoreI18n.URL).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepositoryLog> DOMAIN = BUILDER.addStringField("domain", o->o.m_domain, (o,v)->o.m_domain=v).setTitle(CoreI18n.DOMAIN).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepositoryLog> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepositoryLog> PASSWORD = BUILDER.addStringField("password", o->o.m_password, (o,v)->o.m_password=v).setTitle(CoreI18n.PASSWORD).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbTableKey<AGStoredFileRepositoryLog, Tuple2<AGStoredFileRepository, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(STORED_FILE_REPOSITORY, TRANSACTION));
	public static final IDbKey<AGStoredFileRepositoryLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGStoredFileRepositoryLog, Tuple2<AGStoredFileRepository, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGStoredFileRepositoryLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getStoredFileRepositoryID() {

		return getValueId(STORED_FILE_REPOSITORY);
	}

	public final AGStoredFileRepository getStoredFileRepository() {

		return getValue(STORED_FILE_REPOSITORY);
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

	public final AGStoredFileRepositoryLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getUrl() {

		return getValue(URL);
	}

	public final AGStoredFileRepositoryLog setUrl(String value) {

		return setValue(URL, value);
	}

	public final String getDomain() {

		return getValue(DOMAIN);
	}

	public final AGStoredFileRepositoryLog setDomain(String value) {

		return setValue(DOMAIN, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGStoredFileRepositoryLog setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getPassword() {

		return getValue(PASSWORD);
	}

	public final AGStoredFileRepositoryLog setPassword(String value) {

		return setValue(PASSWORD, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGStoredFileRepositoryLog, Tuple2<AGStoredFileRepository, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGStoredFileRepository m_storedFileRepository;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_url;
	private String m_domain;
	private String m_username;
	private String m_password;
}

