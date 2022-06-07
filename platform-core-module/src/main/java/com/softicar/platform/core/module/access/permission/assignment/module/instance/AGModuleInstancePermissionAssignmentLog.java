package com.softicar.platform.core.module.access.permission.assignment.module.instance;

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
 * This is the automatically generated class AGModuleInstancePermissionAssignmentLog for
 * database table <i>Core.ModuleInstancePermissionAssignmentLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstancePermissionAssignmentLog extends AbstractDbRecord<AGModuleInstancePermissionAssignmentLog, Tuple2<AGModuleInstancePermissionAssignment, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGModuleInstancePermissionAssignmentLog, AGModuleInstancePermissionAssignmentLog, Tuple2<AGModuleInstancePermissionAssignment, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ModuleInstancePermissionAssignmentLog", AGModuleInstancePermissionAssignmentLog::new, AGModuleInstancePermissionAssignmentLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_PERMISSION_ASSIGNMENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_PERMISSION_ASSIGNMENT_LOGS);
	}

	public static final IDbForeignField<AGModuleInstancePermissionAssignmentLog, AGModuleInstancePermissionAssignment> ASSIGNMENT = BUILDER.addForeignField("assignment", o->o.m_assignment, (o,v)->o.m_assignment=v, AGModuleInstancePermissionAssignment.ID).setTitle(CoreI18n.ASSIGNMENT);
	public static final IDbForeignField<AGModuleInstancePermissionAssignmentLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGModuleInstancePermissionAssignmentLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGModuleInstancePermissionAssignmentLog, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(CoreI18n.PERMISSION).setNullable().setDefault(null);
	public static final IDbTableKey<AGModuleInstancePermissionAssignmentLog, Tuple2<AGModuleInstancePermissionAssignment, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ASSIGNMENT, TRANSACTION));
	public static final IDbKey<AGModuleInstancePermissionAssignmentLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGModuleInstancePermissionAssignmentLog, Tuple2<AGModuleInstancePermissionAssignment, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGModuleInstancePermissionAssignmentLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getAssignmentID() {

		return getValueId(ASSIGNMENT);
	}

	public final AGModuleInstancePermissionAssignment getAssignment() {

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

	public final AGModuleInstancePermissionAssignmentLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getPermissionID() {

		return getValueId(PERMISSION);
	}

	public final AGUuid getPermission() {

		return getValue(PERMISSION);
	}

	public final AGModuleInstancePermissionAssignmentLog setPermission(AGUuid value) {

		return setValue(PERMISSION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGModuleInstancePermissionAssignmentLog, Tuple2<AGModuleInstancePermissionAssignment, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstancePermissionAssignment m_assignment;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_permission;
}

