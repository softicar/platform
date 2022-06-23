package com.softicar.platform.core.module.role.permission;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGRolePermissionLog for
 * database table <i>Core.RolePermissionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGRolePermissionLog extends AbstractDbRecord<AGRolePermissionLog, Tuple2<AGRolePermission, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGRolePermissionLog, AGRolePermissionLog, Tuple2<AGRolePermission, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "RolePermissionLog", AGRolePermissionLog::new, AGRolePermissionLog.class);
	static {
		BUILDER.setTitle(CoreI18n.ROLE_PERMISSION_LOG);
		BUILDER.setPluralTitle(CoreI18n.ROLE_PERMISSION_LOGS);
	}

	public static final IDbForeignField<AGRolePermissionLog, AGRolePermission> ROLE_USER = BUILDER.addForeignField("roleUser", o->o.m_roleUser, (o,v)->o.m_roleUser=v, AGRolePermission.ID).setTitle(CoreI18n.ROLE_USER);
	public static final IDbForeignField<AGRolePermissionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbBooleanField<AGRolePermissionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGRolePermissionLog, AGRole> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGRole.ID).setTitle(CoreI18n.ROLE).setNullable().setDefault(null);
	public static final IDbForeignField<AGRolePermissionLog, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.ID).setTitle(CoreI18n.MODULE_INSTANCE).setNullable().setDefault(null);
	public static final IDbForeignField<AGRolePermissionLog, AGUuid> PERMISSION_UUID = BUILDER.addForeignField("permissionUuid", o->o.m_permissionUuid, (o,v)->o.m_permissionUuid=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION_UUID).setNullable().setDefault(null);
	public static final IDbTableKey<AGRolePermissionLog, Tuple2<AGRolePermission, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ROLE_USER, TRANSACTION));
	public static final DbRecordTable<AGRolePermissionLog, Tuple2<AGRolePermission, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGRolePermissionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getRoleUserID() {

		return getValueId(ROLE_USER);
	}

	public final AGRolePermission getRoleUser() {

		return getValue(ROLE_USER);
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

	public final AGRolePermissionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGRole getRole() {

		return getValue(ROLE);
	}

	public final AGRolePermissionLog setRole(AGRole value) {

		return setValue(ROLE, value);
	}

	public final Integer getModuleInstanceID() {

		return getValueId(MODULE_INSTANCE);
	}

	public final AGModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGRolePermissionLog setModuleInstance(AGModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Integer getPermissionUuidID() {

		return getValueId(PERMISSION_UUID);
	}

	public final AGUuid getPermissionUuid() {

		return getValue(PERMISSION_UUID);
	}

	public final AGRolePermissionLog setPermissionUuid(AGUuid value) {

		return setValue(PERMISSION_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGRolePermissionLog, Tuple2<AGRolePermission, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGRolePermission m_roleUser;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGRole m_role;
	private AGModuleInstance m_moduleInstance;
	private AGUuid m_permissionUuid;
}

