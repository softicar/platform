package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceState;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGMaintenanceWindow for
 * database table <i>Core.MaintenanceWindow</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceWindowGenerated extends AbstractDbObject<AGMaintenanceWindow> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGMaintenanceWindow, AGMaintenanceWindowGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "MaintenanceWindow", AGMaintenanceWindow::new, AGMaintenanceWindow.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE_WINDOW);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCE_WINDOWS);
	}

	public static final IDbIdField<AGMaintenanceWindow> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbDayTimeField<AGMaintenanceWindow> EXPECTED_START = BUILDER.addDayTimeField("expectedStart", o->o.m_expectedStart, (o,v)->o.m_expectedStart=v).setTitle(CoreI18n.EXPECTED_START);
	public static final IDbDayTimeField<AGMaintenanceWindow> EXPECTED_END = BUILDER.addDayTimeField("expectedEnd", o->o.m_expectedEnd, (o,v)->o.m_expectedEnd=v).setTitle(CoreI18n.EXPECTED_END);
	public static final IDbForeignField<AGMaintenanceWindow, AGMaintenanceState> STATE = BUILDER.addForeignField("state", o->o.m_state, (o,v)->o.m_state=v, AGMaintenanceState.ID).setTitle(CoreI18n.STATE);
	public static final IDbStringField<AGMaintenanceWindow> COMMENT = BUILDER.addStringField("comment", o->o.m_comment, (o,v)->o.m_comment=v).setTitle(CoreI18n.COMMENT).setNullable().setDefault("").setLengthBits(8);
	public static final AGMaintenanceWindowTable TABLE = new AGMaintenanceWindowTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGMaintenanceWindow> createSelect() {

		return TABLE.createSelect();
	}

	public static AGMaintenanceWindow get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final DayTime getExpectedStart() {

		return getValue(EXPECTED_START);
	}

	public final AGMaintenanceWindow setExpectedStart(DayTime value) {

		return setValue(EXPECTED_START, value);
	}

	public final DayTime getExpectedEnd() {

		return getValue(EXPECTED_END);
	}

	public final AGMaintenanceWindow setExpectedEnd(DayTime value) {

		return setValue(EXPECTED_END, value);
	}

	public final Integer getStateID() {

		return getValueId(STATE);
	}

	public final AGMaintenanceState getState() {

		return getValue(STATE);
	}

	public final AGMaintenanceWindow setState(AGMaintenanceState value) {

		return setValue(STATE, value);
	}

	public final String getComment() {

		return getValue(COMMENT);
	}

	public final AGMaintenanceWindow setComment(String value) {

		return setValue(COMMENT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGMaintenanceWindowTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private DayTime m_expectedStart;
	private DayTime m_expectedEnd;
	private AGMaintenanceState m_state;
	private String m_comment;
}

