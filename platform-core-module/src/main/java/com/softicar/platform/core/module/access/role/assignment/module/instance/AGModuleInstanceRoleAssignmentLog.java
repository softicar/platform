package com.softicar.platform.core.module.access.role.assignment.module.instance;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGModuleInstanceRoleAssignmentLog for
 * database table <i>Core.ModuleInstanceRoleAssignmentLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceRoleAssignmentLog extends AbstractDbRecord<AGModuleInstanceRoleAssignmentLog, Tuple2<AGModuleInstanceRoleAssignment, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGModuleInstanceRoleAssignmentLog, AGModuleInstanceRoleAssignmentLog, Tuple2<AGModuleInstanceRoleAssignment, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ModuleInstanceRoleAssignmentLog", AGModuleInstanceRoleAssignmentLog::new, AGModuleInstanceRoleAssignmentLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_ROLE_ASSIGNMENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_ROLE_ASSIGNMENT_LOGS);
	}

	public static final IDbForeignField<AGModuleInstanceRoleAssignmentLog, AGModuleInstanceRoleAssignment> ASSIGNMENT = BUILDER.addForeignField("assignment", o->o.m_assignment, (o,v)->o.m_assignment=v, AGModuleInstanceRoleAssignment.ID).setTitle(CoreI18n.ASSIGNMENT);
	public static final IDbForeignField<AGModuleInstanceRoleAssignmentLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGModuleInstanceRoleAssignmentLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGModuleInstanceRoleAssignmentLog, AGUuid> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGUuid.ID).setTitle(CoreI18n.ROLE).setNullable().setDefault(null);
	public static final IDbTableKey<AGModuleInstanceRoleAssignmentLog, Tuple2<AGModuleInstanceRoleAssignment, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ASSIGNMENT, TRANSACTION));
	public static final IDbKey<AGModuleInstanceRoleAssignmentLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGModuleInstanceRoleAssignmentLog, Tuple2<AGModuleInstanceRoleAssignment, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGModuleInstanceRoleAssignmentLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getAssignmentID() {

		return getValueId(ASSIGNMENT);
	}

	public final AGModuleInstanceRoleAssignment getAssignment() {

		return getValue(ASSIGNMENT);
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

	public final AGModuleInstanceRoleAssignmentLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGUuid getRole() {

		return getValue(ROLE);
	}

	public final AGModuleInstanceRoleAssignmentLog setRole(AGUuid value) {

		return setValue(ROLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGModuleInstanceRoleAssignmentLog, Tuple2<AGModuleInstanceRoleAssignment, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstanceRoleAssignment m_assignment;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_role;
}

