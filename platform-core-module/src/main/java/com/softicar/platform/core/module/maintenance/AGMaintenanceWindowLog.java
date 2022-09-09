package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceState;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGMaintenanceWindowLog for
 * database table <i>Core.MaintenanceWindowLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceWindowLog extends AbstractDbRecord<AGMaintenanceWindowLog, Tuple2<AGMaintenanceWindow, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGMaintenanceWindowLog, AGMaintenanceWindowLog, Tuple2<AGMaintenanceWindow, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "MaintenanceWindowLog", AGMaintenanceWindowLog::new, AGMaintenanceWindowLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE_WINDOW_LOG);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCE_WINDOW_LOGS);
	}

	public static final IDbForeignField<AGMaintenanceWindowLog, AGMaintenanceWindow> MAINTENANCE_WINDOW = BUILDER.addForeignField("maintenanceWindow", o->o.m_maintenanceWindow, (o,v)->o.m_maintenanceWindow=v, AGMaintenanceWindow.ID).setTitle(CoreI18n.MAINTENANCE_WINDOW).setForeignKeyName("MaintenanceWindowLog_ibfk_1");
	public static final IDbForeignField<AGMaintenanceWindowLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setForeignKeyName("MaintenanceWindowLog_ibfk_2");
	public static final IDbDayTimeField<AGMaintenanceWindowLog> EXPECTED_START = BUILDER.addDayTimeField("expectedStart", o->o.m_expectedStart, (o,v)->o.m_expectedStart=v).setTitle(CoreI18n.EXPECTED_START).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGMaintenanceWindowLog> EXPECTED_END = BUILDER.addDayTimeField("expectedEnd", o->o.m_expectedEnd, (o,v)->o.m_expectedEnd=v).setTitle(CoreI18n.EXPECTED_END).setNullable().setDefault(null);
	public static final IDbForeignField<AGMaintenanceWindowLog, AGMaintenanceState> STATE = BUILDER.addForeignField("state", o->o.m_state, (o,v)->o.m_state=v, AGMaintenanceState.ID).setTitle(CoreI18n.STATE).setNullable().setDefault(null).setForeignKeyName("MaintenanceWindowLog_ibfk_3");
	public static final IDbStringField<AGMaintenanceWindowLog> COMMENT = BUILDER.addStringField("comment", o->o.m_comment, (o,v)->o.m_comment=v).setTitle(CoreI18n.COMMENT).setNullable().setDefault(null).setLengthBits(8);
	public static final IDbTableKey<AGMaintenanceWindowLog, Tuple2<AGMaintenanceWindow, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(MAINTENANCE_WINDOW, TRANSACTION));
	public static final IDbKey<AGMaintenanceWindowLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGMaintenanceWindowLog> IK_STATE = BUILDER.addIndexKey("state", STATE);
	public static final DbRecordTable<AGMaintenanceWindowLog, Tuple2<AGMaintenanceWindow, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGMaintenanceWindowLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getMaintenanceWindowID() {

		return getValueId(MAINTENANCE_WINDOW);
	}

	public final AGMaintenanceWindow getMaintenanceWindow() {

		return getValue(MAINTENANCE_WINDOW);
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

	public final AGMaintenanceWindowLog setExpectedStart(DayTime value) {

		return setValue(EXPECTED_START, value);
	}

	public final DayTime getExpectedEnd() {

		return getValue(EXPECTED_END);
	}

	public final AGMaintenanceWindowLog setExpectedEnd(DayTime value) {

		return setValue(EXPECTED_END, value);
	}

	public final Integer getStateID() {

		return getValueId(STATE);
	}

	public final AGMaintenanceState getState() {

		return getValue(STATE);
	}

	public final AGMaintenanceWindowLog setState(AGMaintenanceState value) {

		return setValue(STATE, value);
	}

	public final String getComment() {

		return getValue(COMMENT);
	}

	public final AGMaintenanceWindowLog setComment(String value) {

		return setValue(COMMENT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGMaintenanceWindowLog, Tuple2<AGMaintenanceWindow, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGMaintenanceWindow m_maintenanceWindow;
	private AGTransaction m_transaction;
	private DayTime m_expectedStart;
	private DayTime m_expectedEnd;
	private AGMaintenanceState m_state;
	private String m_comment;
}

