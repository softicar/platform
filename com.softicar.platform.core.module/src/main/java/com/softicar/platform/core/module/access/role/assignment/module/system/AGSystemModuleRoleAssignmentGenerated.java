package com.softicar.platform.core.module.access.role.assignment.module.system;

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
 * This is the automatically generated class AGSystemModuleRoleAssignment for
 * database table <i>Core.SystemModuleRoleAssignment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemModuleRoleAssignmentGenerated extends AbstractDbObject<AGSystemModuleRoleAssignment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGSystemModuleRoleAssignment, AGSystemModuleRoleAssignmentGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "SystemModuleRoleAssignment", AGSystemModuleRoleAssignment::new, AGSystemModuleRoleAssignment.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_MODULE_ROLE_ASSIGNMENT);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_MODULE_ROLE_ASSIGNMENTS);
	}

	public static final IDbIdField<AGSystemModuleRoleAssignment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGSystemModuleRoleAssignment, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbForeignField<AGSystemModuleRoleAssignment, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGSystemModuleRoleAssignment, AGUuid> MODULE = BUILDER.addForeignField("module", o->o.m_module, (o,v)->o.m_module=v, AGUuid.ID).setTitle(CoreI18n.MODULE);
	public static final IDbForeignField<AGSystemModuleRoleAssignment, AGUuid> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGUuid.ID).setTitle(CoreI18n.ROLE);
	public static final IDbBooleanField<AGSystemModuleRoleAssignment> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGSystemModuleRoleAssignment> UK_USER_MODULE_ROLE = BUILDER.addUniqueKey("userModuleRole", USER, MODULE, ROLE);
	public static final IDbKey<AGSystemModuleRoleAssignment> IK_MODULE = BUILDER.addIndexKey("module", MODULE);
	public static final IDbKey<AGSystemModuleRoleAssignment> IK_ROLE = BUILDER.addIndexKey("role", ROLE);
	public static final IDbKey<AGSystemModuleRoleAssignment> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGSystemModuleRoleAssignmentTable TABLE = new AGSystemModuleRoleAssignmentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGSystemModuleRoleAssignment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGSystemModuleRoleAssignment get(Integer id) {

		return TABLE.get(id);
	}

	public static AGSystemModuleRoleAssignment loadByUserAndModuleAndRole(AGUser user, AGUuid module, AGUuid role) {

		return TABLE//
				.createSelect()
				.where(USER.equal(user))
				.where(MODULE.equal(module))
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

	public final AGSystemModuleRoleAssignment setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGSystemModuleRoleAssignment setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getModuleID() {

		return getValueId(MODULE);
	}

	public final AGUuid getModule() {

		return getValue(MODULE);
	}

	public final AGSystemModuleRoleAssignment setModule(AGUuid value) {

		return setValue(MODULE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGUuid getRole() {

		return getValue(ROLE);
	}

	public final AGSystemModuleRoleAssignment setRole(AGUuid value) {

		return setValue(ROLE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGSystemModuleRoleAssignment setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGSystemModuleRoleAssignmentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGUser m_user;
	private AGUuid m_module;
	private AGUuid m_role;
	private Boolean m_active;
}

