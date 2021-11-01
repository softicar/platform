package com.softicar.platform.core.module.access.role.assignment.module.instance;

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
 * This is the automatically generated class AGModuleInstanceRoleAssignment for
 * database table <i>Core.ModuleInstanceRoleAssignment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceRoleAssignmentGenerated extends AbstractDbObject<AGModuleInstanceRoleAssignment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGModuleInstanceRoleAssignment, AGModuleInstanceRoleAssignmentGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ModuleInstanceRoleAssignment", AGModuleInstanceRoleAssignment::new, AGModuleInstanceRoleAssignment.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_ROLE_ASSIGNMENT);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_ROLE_ASSIGNMENTS);
	}

	public static final IDbIdField<AGModuleInstanceRoleAssignment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGModuleInstanceRoleAssignment, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbForeignField<AGModuleInstanceRoleAssignment, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGModuleInstanceRoleAssignment, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.ID).setTitle(CoreI18n.MODULE_INSTANCE).setCascade(false, true);
	public static final IDbForeignField<AGModuleInstanceRoleAssignment, AGUuid> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGUuid.ID).setTitle(CoreI18n.ROLE);
	public static final IDbBooleanField<AGModuleInstanceRoleAssignment> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGModuleInstanceRoleAssignment> UK_USER_MODULE_INSTANCE_ROLE = BUILDER.addUniqueKey("userModuleInstanceRole", USER, MODULE_INSTANCE, ROLE);
	public static final IDbKey<AGModuleInstanceRoleAssignment> IK_MODULE_INSTANCE = BUILDER.addIndexKey("moduleInstance", MODULE_INSTANCE);
	public static final IDbKey<AGModuleInstanceRoleAssignment> IK_ROLE = BUILDER.addIndexKey("role", ROLE);
	public static final IDbKey<AGModuleInstanceRoleAssignment> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGModuleInstanceRoleAssignmentTable TABLE = new AGModuleInstanceRoleAssignmentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGModuleInstanceRoleAssignment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGModuleInstanceRoleAssignment get(Integer id) {

		return TABLE.get(id);
	}

	public static AGModuleInstanceRoleAssignment loadByUserAndModuleInstanceAndRole(AGUser user, AGModuleInstance moduleInstance, AGUuid role) {

		return TABLE//
				.createSelect()
				.where(USER.equal(user))
				.where(MODULE_INSTANCE.equal(moduleInstance))
				.where(ROLE.equal(role))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGModuleInstanceRoleAssignment setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGModuleInstanceRoleAssignment setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getModuleInstanceID() {

		return getValueId(MODULE_INSTANCE);
	}

	public final AGModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGModuleInstanceRoleAssignment setModuleInstance(AGModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGUuid getRole() {

		return getValue(ROLE);
	}

	public final AGModuleInstanceRoleAssignment setRole(AGUuid value) {

		return setValue(ROLE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGModuleInstanceRoleAssignment setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGModuleInstanceRoleAssignmentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGUser m_user;
	private AGModuleInstance m_moduleInstance;
	private AGUuid m_role;
	private Boolean m_active;
}

