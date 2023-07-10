package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserPasswordResetRequest for
 * database table <i>Core.UserPasswordResetRequest</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserPasswordResetRequestGenerated extends AbstractDbObject<AGUserPasswordResetRequest> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserPasswordResetRequest, AGUserPasswordResetRequestGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "UserPasswordResetRequest", AGUserPasswordResetRequest::new, AGUserPasswordResetRequest.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_PASSWORD_RESET_REQUEST);
		BUILDER.setPluralTitle(CoreI18n.USER_PASSWORD_RESET_REQUESTS);
	}

	public static final IDbIdField<AGUserPasswordResetRequest> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserPasswordResetRequest, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setForeignKeyName("UserPasswordReset_ibfk_1");
	public static final IDbStringField<AGUserPasswordResetRequest> UUID = BUILDER.addStringField("uuid", o->o.m_uuid, (o,v)->o.m_uuid=v).setTitle(CoreI18n.UUID).setMaximumLength(255);
	public static final IDbDayTimeField<AGUserPasswordResetRequest> CREATED_AT = BUILDER.addDayTimeField("createdAt", o->o.m_createdAt, (o,v)->o.m_createdAt=v).setTitle(CoreI18n.CREATED_AT).setDefaultNow();
	public static final IDbBooleanField<AGUserPasswordResetRequest> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGUserPasswordResetRequest> UK_UUID = BUILDER.addUniqueKey("uuid", UUID);
	public static final IDbKey<AGUserPasswordResetRequest> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final DbObjectTable<AGUserPasswordResetRequest> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserPasswordResetRequest> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserPasswordResetRequest get(Integer id) {

		return TABLE.get(id);
	}

	public static AGUserPasswordResetRequest loadByUuid(String uuid) {

		return TABLE//
				.createSelect()
				.where(UUID.isEqual(uuid))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGUserPasswordResetRequest setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final String getUuid() {

		return getValue(UUID);
	}

	public final AGUserPasswordResetRequest setUuid(String value) {

		return setValue(UUID, value);
	}

	public final DayTime getCreatedAt() {

		return getValue(CREATED_AT);
	}

	public final AGUserPasswordResetRequest setCreatedAt(DayTime value) {

		return setValue(CREATED_AT, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGUserPasswordResetRequest setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGUserPasswordResetRequest> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private String m_uuid;
	private DayTime m_createdAt;
	private Boolean m_active;
}

