package com.softicar.platform.core.module.file.stored.repository;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileRepository for
 * database table <i>Core.StoredFileRepository</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileRepositoryGenerated extends AbstractDbObject<AGStoredFileRepository> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileRepository, AGStoredFileRepositoryGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileRepository", AGStoredFileRepository::new, AGStoredFileRepository.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_REPOSITORY);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_REPOSITORIES);
	}

	public static final IDbIdField<AGStoredFileRepository> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGStoredFileRepository> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGStoredFileRepository> URL = BUILDER.addStringField("url", o->o.m_url, (o,v)->o.m_url=v).setTitle(CoreI18n.URL).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepository> DOMAIN = BUILDER.addStringField("domain", o->o.m_domain, (o,v)->o.m_domain=v).setTitle(CoreI18n.DOMAIN).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepository> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setMaximumLength(255);
	public static final IDbStringField<AGStoredFileRepository> PASSWORD = BUILDER.addStringField("password", o->o.m_password, (o,v)->o.m_password=v).setTitle(CoreI18n.PASSWORD).setMaximumLength(255);
	public static final AGStoredFileRepositoryTable TABLE = new AGStoredFileRepositoryTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileRepository> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileRepository get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGStoredFileRepository setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getUrl() {

		return getValue(URL);
	}

	public final AGStoredFileRepository setUrl(String value) {

		return setValue(URL, value);
	}

	public final String getDomain() {

		return getValue(DOMAIN);
	}

	public final AGStoredFileRepository setDomain(String value) {

		return setValue(DOMAIN, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGStoredFileRepository setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getPassword() {

		return getValue(PASSWORD);
	}

	public final AGStoredFileRepository setPassword(String value) {

		return setValue(PASSWORD, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStoredFileRepositoryTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_url;
	private String m_domain;
	private String m_username;
	private String m_password;
}

