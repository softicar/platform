package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGSystemModuleRoleAssignmentLog for
 * database table <i>Core.SystemModuleRoleAssignmentLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemModuleRoleAssignmentLog extends AbstractDbRecord<AGSystemModuleRoleAssignmentLog, Tuple2<AGSystemModuleRoleAssignment, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGSystemModuleRoleAssignmentLog, AGSystemModuleRoleAssignmentLog, Tuple2<AGSystemModuleRoleAssignment, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "SystemModuleRoleAssignmentLog", AGSystemModuleRoleAssignmentLog::new, AGSystemModuleRoleAssignmentLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_MODULE_ROLE_ASSIGNMENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_MODULE_ROLE_ASSIGNMENT_LOGS);
	}

	public static final IDbForeignField<AGSystemModuleRoleAssignmentLog, AGSystemModuleRoleAssignment> ASSIGNMENT = BUILDER.addForeignField("assignment", o->o.m_assignment, (o,v)->o.m_assignment=v, AGSystemModuleRoleAssignment.ID).setTitle(CoreI18n.ASSIGNMENT);
	public static final IDbForeignField<AGSystemModuleRoleAssignmentLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGSystemModuleRoleAssignmentLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGSystemModuleRoleAssignmentLog, Tuple2<AGSystemModuleRoleAssignment, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ASSIGNMENT, TRANSACTION));
	public static final IDbKey<AGSystemModuleRoleAssignmentLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGSystemModuleRoleAssignmentLog, Tuple2<AGSystemModuleRoleAssignment, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGSystemModuleRoleAssignmentLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getAssignmentID() {

		return getValueId(ASSIGNMENT);
	}

	public final AGSystemModuleRoleAssignment getAssignment() {

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

	public final AGSystemModuleRoleAssignmentLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGSystemModuleRoleAssignmentLog, Tuple2<AGSystemModuleRoleAssignment, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGSystemModuleRoleAssignment m_assignment;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

