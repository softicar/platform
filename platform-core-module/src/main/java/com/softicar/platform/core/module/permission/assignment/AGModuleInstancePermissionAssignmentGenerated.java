package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
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
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true).setForeignKeyName("ModuleInstancePermissionAssignment_ibfk_1");
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setForeignKeyName("ModuleInstancePermissionAssignment_ibfk_2");
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGModuleInstanceBase> MODULE_INSTANCE_BASE = BUILDER.addForeignField("moduleInstanceBase", o->o.m_moduleInstanceBase, (o,v)->o.m_moduleInstanceBase=v, AGModuleInstanceBase.ID).setTitle(CoreI18n.MODULE_INSTANCE_BASE).setCascade(false, true).setForeignKeyName("ModuleInstancePermissionAssignment_ibfk_3");
	public static final IDbForeignField<AGModuleInstancePermissionAssignment, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION).setForeignKeyName("ModuleInstancePermissionAssignment_ibfk_4");
	public static final IDbBooleanField<AGModuleInstancePermissionAssignment> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGModuleInstancePermissionAssignment> UK_USER_MODULE_INSTANCE_BASE_PERMISSION = BUILDER.addUniqueKey("userModuleInstanceBasePermission", USER, MODULE_INSTANCE_BASE, PERMISSION);
	public static final IDbKey<AGModuleInstancePermissionAssignment> IK_MODULE_INSTANCE_BASE = BUILDER.addIndexKey("moduleInstanceBase", MODULE_INSTANCE_BASE);
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

	public static AGModuleInstancePermissionAssignment loadByUserAndModuleInstanceBaseAndPermission(AGUser user, AGModuleInstanceBase moduleInstanceBase, AGUuid permission) {

		return TABLE//
				.createSelect()
				.where(USER.isEqual(user))
				.where(MODULE_INSTANCE_BASE.isEqual(moduleInstanceBase))
				.where(PERMISSION.isEqual(permission))
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

	public final Integer getModuleInstanceBaseID() {

		return getValueId(MODULE_INSTANCE_BASE);
	}

	public final AGModuleInstanceBase getModuleInstanceBase() {

		return getValue(MODULE_INSTANCE_BASE);
	}

	public final AGModuleInstancePermissionAssignment setModuleInstanceBase(AGModuleInstanceBase value) {

		return setValue(MODULE_INSTANCE_BASE, value);
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
	private AGModuleInstanceBase m_moduleInstanceBase;
	private AGUuid m_permission;
	private Boolean m_active;
}

