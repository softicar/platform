package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.status.AGMaintenanceStatus;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGMaintenanceLog for
 * database table <i>Core.MaintenanceLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceLog extends AbstractDbRecord<AGMaintenanceLog, Tuple2<AGMaintenance, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGMaintenanceLog, AGMaintenanceLog, Tuple2<AGMaintenance, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "MaintenanceLog", AGMaintenanceLog::new, AGMaintenanceLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE_LOG);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCE_LOGS);
	}

	public static final IDbForeignField<AGMaintenanceLog, AGMaintenance> MAINTENANCE = BUILDER.addForeignField("maintenance", o->o.m_maintenance, (o,v)->o.m_maintenance=v, AGMaintenance.ID).setTitle(CoreI18n.MAINTENANCE);
	public static final IDbForeignField<AGMaintenanceLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbDayTimeField<AGMaintenanceLog> EXPECTED_START = BUILDER.addDayTimeField("expectedStart", o->o.m_expectedStart, (o,v)->o.m_expectedStart=v).setTitle(CoreI18n.EXPECTED_START).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGMaintenanceLog> EXPECTED_END = BUILDER.addDayTimeField("expectedEnd", o->o.m_expectedEnd, (o,v)->o.m_expectedEnd=v).setTitle(CoreI18n.EXPECTED_END).setNullable().setDefault(null);
	public static final IDbForeignField<AGMaintenanceLog, AGMaintenanceStatus> STATUS = BUILDER.addForeignField("status", o->o.m_status, (o,v)->o.m_status=v, AGMaintenanceStatus.ID).setTitle(CoreI18n.STATUS).setNullable().setDefault(null);
	public static final IDbStringField<AGMaintenanceLog> COMMENT = BUILDER.addStringField("comment", o->o.m_comment, (o,v)->o.m_comment=v).setTitle(CoreI18n.COMMENT).setNullable().setDefault(null);
	public static final IDbTableKey<AGMaintenanceLog, Tuple2<AGMaintenance, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(MAINTENANCE, TRANSACTION));
	public static final DbRecordTable<AGMaintenanceLog, Tuple2<AGMaintenance, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGMaintenanceLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getMaintenanceID() {

		return getValueId(MAINTENANCE);
	}

	public final AGMaintenance getMaintenance() {

		return getValue(MAINTENANCE);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final DayTime getExpectedStart() {

		return getValue(EXPECTED_START);
	}

	public final AGMaintenanceLog setExpectedStart(DayTime value) {

		return setValue(EXPECTED_START, value);
	}

	public final DayTime getExpectedEnd() {

		return getValue(EXPECTED_END);
	}

	public final AGMaintenanceLog setExpectedEnd(DayTime value) {

		return setValue(EXPECTED_END, value);
	}

	public final Integer getStatusID() {

		return getValueId(STATUS);
	}

	public final AGMaintenanceStatus getStatus() {

		return getValue(STATUS);
	}

	public final AGMaintenanceLog setStatus(AGMaintenanceStatus value) {

		return setValue(STATUS, value);
	}

	public final String getComment() {

		return getValue(COMMENT);
	}

	public final AGMaintenanceLog setComment(String value) {

		return setValue(COMMENT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGMaintenanceLog, Tuple2<AGMaintenance, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGMaintenance m_maintenance;
	private AGTransaction m_transaction;
	private DayTime m_expectedStart;
	private DayTime m_expectedEnd;
	private AGMaintenanceStatus m_status;
	private String m_comment;
}

