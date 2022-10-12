package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUuid for
 * database table <i>Core.Uuid</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUuidGenerated extends AbstractDbObject<AGUuid> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUuid, AGUuidGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Uuid", AGUuid::new, AGUuid.class);
	static {
		BUILDER.setTitle(CoreI18n.UUID);
		BUILDER.setPluralTitle(CoreI18n.UUIDS);
	}

	public static final IDbIdField<AGUuid> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGUuid> UUID_STRING = BUILDER.addStringField("uuidString", o->o.m_uuidString, (o,v)->o.m_uuidString=v).setTitle(CoreI18n.UUID_STRING).setMaximumLength(36);
	public static final IDbKey<AGUuid> UK_UUID_STRING = BUILDER.addUniqueKey("uuidString", UUID_STRING);
	public static final AGUuidTable TABLE = new AGUuidTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUuid> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUuid get(Integer id) {

		return TABLE.get(id);
	}

	public static AGUuid loadByUuidString(String uuidString) {

		return TABLE//
				.createSelect()
				.where(UUID_STRING.isEqual(uuidString))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getUuidString() {

		return getValue(UUID_STRING);
	}

	public final AGUuid setUuidString(String value) {

		return setValue(UUID_STRING, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUuidTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_uuidString;
}

