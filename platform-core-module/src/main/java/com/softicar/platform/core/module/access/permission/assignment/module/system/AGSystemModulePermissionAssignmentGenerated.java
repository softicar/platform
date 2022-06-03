package com.softicar.platform.core.module.access.permission.assignment.module.system;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
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
 * This is the automatically generated class AGSystemModulePermissionAssignment for
 * database table <i>Core.SystemModulePermissionAssignment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemModulePermissionAssignmentGenerated extends AbstractDbObject<AGSystemModulePermissionAssignment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGSystemModulePermissionAssignment, AGSystemModulePermissionAssignmentGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "SystemModulePermissionAssignment", AGSystemModulePermissionAssignment::new, AGSystemModulePermissionAssignment.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_MODULE_PERMISSION_ASSIGNMENT);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_MODULE_PERMISSION_ASSIGNMENTS);
	}

	public static final IDbIdField<AGSystemModulePermissionAssignment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGSystemModulePermissionAssignment, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbForeignField<AGSystemModulePermissionAssignment, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGSystemModulePermissionAssignment, AGUuid> MODULE = BUILDER.addForeignField("module", o->o.m_module, (o,v)->o.m_module=v, AGUuid.ID).setTitle(CoreI18n.MODULE);
	public static final IDbForeignField<AGSystemModulePermissionAssignment, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION);
	public static final IDbBooleanField<AGSystemModulePermissionAssignment> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGSystemModulePermissionAssignment> UK_USER_MODULE_PERMISSION = BUILDER.addUniqueKey("userModulePermission", USER, MODULE, PERMISSION);
	public static final IDbKey<AGSystemModulePermissionAssignment> IK_MODULE = BUILDER.addIndexKey("module", MODULE);
	public static final IDbKey<AGSystemModulePermissionAssignment> IK_PERMISSION = BUILDER.addIndexKey("permission", PERMISSION);
	public static final IDbKey<AGSystemModulePermissionAssignment> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGSystemModulePermissionAssignmentTable TABLE = new AGSystemModulePermissionAssignmentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGSystemModulePermissionAssignment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGSystemModulePermissionAssignment get(Integer id) {

		return TABLE.get(id);
	}

	public static AGSystemModulePermissionAssignment loadByUserAndModuleAndPermission(AGUser user, AGUuid module, AGUuid permission) {

		return TABLE//
				.createSelect()
				.where(USER.equal(user))
				.where(MODULE.equal(module))
				.where(PERMISSION.equal(permission))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGSystemModulePermissionAssignment setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGSystemModulePermissionAssignment setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getModuleID() {

		return getValueId(MODULE);
	}

	public final AGUuid getModule() {

		return getValue(MODULE);
	}

	public final AGSystemModulePermissionAssignment setModule(AGUuid value) {

		return setValue(MODULE, value);
	}

	public final Integer getPermissionID() {

		return getValueId(PERMISSION);
	}

	public final AGUuid getPermission() {

		return getValue(PERMISSION);
	}

	public final AGSystemModulePermissionAssignment setPermission(AGUuid value) {

		return setValue(PERMISSION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGSystemModulePermissionAssignment setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGSystemModulePermissionAssignmentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGUser m_user;
	private AGUuid m_module;
	private AGUuid m_permission;
	private Boolean m_active;
}

