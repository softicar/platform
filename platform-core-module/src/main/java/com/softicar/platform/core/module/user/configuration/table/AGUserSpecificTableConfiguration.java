package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserSpecificTableConfiguration for
 * database table <i>Core.UserSpecificTableConfiguration</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserSpecificTableConfiguration extends AbstractDbObject<AGUserSpecificTableConfiguration> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserSpecificTableConfiguration, AGUserSpecificTableConfiguration> BUILDER = new DbObjectTableBuilder<>("Core", "UserSpecificTableConfiguration", AGUserSpecificTableConfiguration::new, AGUserSpecificTableConfiguration.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_SPECIFIC_TABLE_CONFIGURATION);
		BUILDER.setPluralTitle(CoreI18n.USER_SPECIFIC_TABLE_CONFIGURATIONS);
	}

	public static final IDbIdField<AGUserSpecificTableConfiguration> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserSpecificTableConfiguration, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setNullable().setDefault(null);
	public static final IDbStringField<AGUserSpecificTableConfiguration> TABLE_PATH_HASH = BUILDER.addStringField("tablePathHash", o->o.m_tablePathHash, (o,v)->o.m_tablePathHash=v).setTitle(CoreI18n.TABLE_PATH_HASH).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGUserSpecificTableConfiguration> COLUMN_TITLES_HASH = BUILDER.addStringField("columnTitlesHash", o->o.m_columnTitlesHash, (o,v)->o.m_columnTitlesHash=v).setTitle(CoreI18n.COLUMN_TITLES_HASH).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGUserSpecificTableConfiguration> SERIALIZATION = BUILDER.addStringField("serialization", o->o.m_serialization, (o,v)->o.m_serialization=v).setTitle(CoreI18n.SERIALIZATION).setDefault("").setLengthBits(16);
	public static final IDbKey<AGUserSpecificTableConfiguration> UK_USER_TABLE_PATH_HASH_COLUMN_TITLES_HASH = BUILDER.addUniqueKey("userTablePathHashColumnTitlesHash", USER, TABLE_PATH_HASH, COLUMN_TITLES_HASH);
	public static final DbObjectTable<AGUserSpecificTableConfiguration> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserSpecificTableConfiguration> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserSpecificTableConfiguration get(Integer id) {

		return TABLE.get(id);
	}

	public static AGUserSpecificTableConfiguration loadByUserAndTablePathHashAndColumnTitlesHash(AGUser user, String tablePathHash, String columnTitlesHash) {

		return TABLE//
				.createSelect()
				.where(USER.equal(user))
				.where(TABLE_PATH_HASH.equal(tablePathHash))
				.where(COLUMN_TITLES_HASH.equal(columnTitlesHash))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGUserSpecificTableConfiguration setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final String getTablePathHash() {

		return getValue(TABLE_PATH_HASH);
	}

	public final AGUserSpecificTableConfiguration setTablePathHash(String value) {

		return setValue(TABLE_PATH_HASH, value);
	}

	public final String getColumnTitlesHash() {

		return getValue(COLUMN_TITLES_HASH);
	}

	public final AGUserSpecificTableConfiguration setColumnTitlesHash(String value) {

		return setValue(COLUMN_TITLES_HASH, value);
	}

	public final String getSerialization() {

		return getValue(SERIALIZATION);
	}

	public final AGUserSpecificTableConfiguration setSerialization(String value) {

		return setValue(SERIALIZATION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGUserSpecificTableConfiguration> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private String m_tablePathHash;
	private String m_columnTitlesHash;
	private String m_serialization;
}

