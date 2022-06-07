package com.softicar.platform.core.module.access.permission.assignment.module.instance;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
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
 * This is the automatically generated class AGModuleInstancePermissionAssignment for
 * database table <i>Core.ModuleInstancePermissionAssignment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstancePermissionAssignmentGenerated extends AbstractDbObject<AGModuleInstancePermissionAssignment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGModuleInstancePermissionAssignment, AGModuleInstancePermissionAssignmentGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ModuleInstancePermissionAssignment", AGModuleInstancePermissionAssignment::new, AGModuleInstancePermissionAssignment.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_PERMISSION_ASSIGNMENT);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_PERMISSION_ASSIGNMENTS);
	}

	public static final IDbIdField<AGModuleInstancePermissionAssignment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.ID).setTitle(CoreI18n.MODULE_INSTANCE).setCascade(false, true);
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION);
	public static final IDbBooleanField<AGModuleInstancePermissionAssignment> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGModuleInstancePermissionAssignment> UK_USER_MODULE_INSTANCE_PERMISSION = BUILDER.addUniqueKey("userModuleInstancePermission", USER, MODULE_INSTANCE, PERMISSION);
	public static final IDbKey<AGModuleInstancePermissionAssignment> IK_MODULE_INSTANCE = BUILDER.addIndexKey("moduleInstance", MODULE_INSTANCE);
	public static final IDbKey<AGModuleInstancePermissionAssignment> IK_PERMISSION = BUILDER.addIndexKey("permission", PERMISSION);
	public static final IDbKey<AGModuleInstancePermissionAssignment> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGModuleInstancePermissionAssignmentTable TABLE = new AGModuleInstancePermissionAssignmentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGModuleInstancePermissionAssignment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGModuleInstancePermissionAssignment get(Integer id) {

		return TABLE.get(id);
	}

	public static AGModuleInstancePermissionAssignment loadByUserAndModuleInstanceAndPermission(AGUser user, AGModuleInstance moduleInstance, AGUuid permission) {

		return TABLE//
				.createSelect()
				.where(USER.equal(user))
				.where(MODULE_INSTANCE.equal(moduleInstance))
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

	public final AGModuleInstancePermissionAssignment setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGModuleInstancePermissionAssignment setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getModuleInstanceID() {

		return getValueId(MODULE_INSTANCE);
	}

	public final AGModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGModuleInstancePermissionAssignment setModuleInstance(AGModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Integer getPermissionID() {

		return getValueId(PERMISSION);
	}

	public final AGUuid getPermission() {

		return getValue(PERMISSION);
	}

	public final AGModuleInstancePermissionAssignment setPermission(AGUuid value) {

		return setValue(PERMISSION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGModuleInstancePermissionAssignment setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGModuleInstancePermissionAssignmentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGUser m_user;
	private AGModuleInstance m_moduleInstance;
	private AGUuid m_permission;
	private Boolean m_active;
}

