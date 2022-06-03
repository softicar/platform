package com.softicar.platform.core.module.access.permission.assignment.module.system;

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
 * This is the automatically generated class AGSystemModulePermissionAssignmentLog for
 * database table <i>Core.SystemModulePermissionAssignmentLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemModulePermissionAssignmentLog extends AbstractDbRecord<AGSystemModulePermissionAssignmentLog, Tuple2<AGSystemModulePermissionAssignment, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGSystemModulePermissionAssignmentLog, AGSystemModulePermissionAssignmentLog, Tuple2<AGSystemModulePermissionAssignment, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "SystemModulePermissionAssignmentLog", AGSystemModulePermissionAssignmentLog::new, AGSystemModulePermissionAssignmentLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_MODULE_PERMISSION_ASSIGNMENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_MODULE_PERMISSION_ASSIGNMENT_LOGS);
	}

	public static final IDbForeignField<AGSystemModulePermissionAssignmentLog, AGSystemModulePermissionAssignment> ASSIGNMENT = BUILDER.addForeignField("assignment", o->o.m_assignment, (o,v)->o.m_assignment=v, AGSystemModulePermissionAssignment.ID).setTitle(CoreI18n.ASSIGNMENT);
	public static final IDbForeignField<AGSystemModulePermissionAssignmentLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGSystemModulePermissionAssignmentLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGSystemModulePermissionAssignmentLog, Tuple2<AGSystemModulePermissionAssignment, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ASSIGNMENT, TRANSACTION));
	public static final IDbKey<AGSystemModulePermissionAssignmentLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGSystemModulePermissionAssignmentLog, Tuple2<AGSystemModulePermissionAssignment, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGSystemModulePermissionAssignmentLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getAssignmentID() {

		return getValueId(ASSIGNMENT);
	}

	public final AGSystemModulePermissionAssignment getAssignment() {

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

	public final AGSystemModulePermissionAssignmentLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGSystemModulePermissionAssignmentLog, Tuple2<AGSystemModulePermissionAssignment, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGSystemModulePermissionAssignment m_assignment;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

