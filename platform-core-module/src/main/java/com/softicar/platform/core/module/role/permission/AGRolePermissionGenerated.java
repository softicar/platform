package com.softicar.platform.core.module.role.permission;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGRolePermission for
 * database table <i>Core.RolePermission</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGRolePermissionGenerated extends AbstractDbObject<AGRolePermission> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGRolePermission, AGRolePermissionGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "RolePermission", AGRolePermission::new, AGRolePermission.class);
	static {
		BUILDER.setTitle(CoreI18n.ROLE_PERMISSION);
		BUILDER.setPluralTitle(CoreI18n.ROLE_PERMISSIONS);
	}

	public static final IDbIdField<AGRolePermission> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGRolePermission> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGRolePermission, AGRole> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGRole.ID).setTitle(CoreI18n.ROLE);
	public static final IDbForeignField<AGRolePermission, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.ID).setTitle(CoreI18n.MODULE_INSTANCE);
	public static final IDbForeignField<AGRolePermission, AGUuid> PERMISSION_UUID = BUILDER.addForeignField("permissionUuid", o->o.m_permissionUuid, (o,v)->o.m_permissionUuid=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION_UUID);
	public static final IDbKey<AGRolePermission> UK_ROLE_PERMISSION_UUID_MODULE_INSTANCE = BUILDER.addUniqueKey("rolePermissionUuidModuleInstance", ROLE, PERMISSION_UUID, MODULE_INSTANCE);
	public static final AGRolePermissionTable TABLE = new AGRolePermissionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGRolePermission> createSelect() {

		return TABLE.createSelect();
	}

	public static AGRolePermission get(Integer id) {

		return TABLE.get(id);
	}

	public static AGRolePermission loadByRoleAndPermissionUuidAndModuleInstance(AGRole role, AGUuid permissionUuid, AGModuleInstance moduleInstance) {

		return TABLE//
				.createSelect()
				.where(ROLE.equal(role))
				.where(PERMISSION_UUID.equal(permissionUuid))
				.where(MODULE_INSTANCE.equal(moduleInstance))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGRolePermission setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGRole getRole() {

		return getValue(ROLE);
	}

	public final AGRolePermission setRole(AGRole value) {

		return setValue(ROLE, value);
	}

	public final Integer getModuleInstanceID() {

		return getValueId(MODULE_INSTANCE);
	}

	public final AGModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGRolePermission setModuleInstance(AGModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Integer getPermissionUuidID() {

		return getValueId(PERMISSION_UUID);
	}

	public final AGUuid getPermissionUuid() {

		return getValue(PERMISSION_UUID);
	}

	public final AGRolePermission setPermissionUuid(AGUuid value) {

		return setValue(PERMISSION_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGRolePermissionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private AGRole m_role;
	private AGModuleInstance m_moduleInstance;
	private AGUuid m_permissionUuid;
}

