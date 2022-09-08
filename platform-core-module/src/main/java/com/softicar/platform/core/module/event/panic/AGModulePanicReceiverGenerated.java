package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGModulePanicReceiver for
 * database table <i>Core.ModulePanicReceiver</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModulePanicReceiverGenerated extends AbstractDbObject<AGModulePanicReceiver> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGModulePanicReceiver, AGModulePanicReceiverGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ModulePanicReceiver", AGModulePanicReceiver::new, AGModulePanicReceiver.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_PANIC_RECEIVER);
		BUILDER.setPluralTitle(CoreI18n.MODULE_PANIC_RECEIVERS);
	}

	public static final IDbIdField<AGModulePanicReceiver> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGModulePanicReceiver, AGUuid> MODULE_UUID = BUILDER.addForeignField("moduleUuid", o->o.m_moduleUuid, (o,v)->o.m_moduleUuid=v, AGUuid.ID).setTitle(CoreI18n.MODULE_UUID).setForeignKeyName("ModulePanicReceiver_ibfk_1");
	public static final IDbBooleanField<AGModulePanicReceiver> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGModulePanicReceiver, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setForeignKeyName("ModulePanicReceiver_ibfk_2");
	public static final IDbKey<AGModulePanicReceiver> UK_MODULE_UUID_USER = BUILDER.addUniqueKey("moduleUuidUser", MODULE_UUID, USER);
	public static final IDbKey<AGModulePanicReceiver> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final AGModulePanicReceiverTable TABLE = new AGModulePanicReceiverTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGModulePanicReceiver> createSelect() {

		return TABLE.createSelect();
	}

	public static AGModulePanicReceiver get(Integer id) {

		return TABLE.get(id);
	}

	public static AGModulePanicReceiver loadByModuleUuidAndUser(AGUuid moduleUuid, AGUser user) {

		return TABLE//
				.createSelect()
				.where(MODULE_UUID.equal(moduleUuid))
				.where(USER.equal(user))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getModuleUuidID() {

		return getValueId(MODULE_UUID);
	}

	public final AGUuid getModuleUuid() {

		return getValue(MODULE_UUID);
	}

	public final AGModulePanicReceiver setModuleUuid(AGUuid value) {

		return setValue(MODULE_UUID, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGModulePanicReceiver setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGModulePanicReceiver setUser(AGUser value) {

		return setValue(USER, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGModulePanicReceiverTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUuid m_moduleUuid;
	private Boolean m_active;
	private AGUser m_user;
}

