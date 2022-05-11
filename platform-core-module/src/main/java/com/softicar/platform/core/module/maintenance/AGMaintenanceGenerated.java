package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.status.AGMaintenanceStatus;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGMaintenance for
 * database table <i>Core.Maintenance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceGenerated extends AbstractDbObject<AGMaintenance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGMaintenance, AGMaintenanceGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Maintenance", AGMaintenance::new, AGMaintenance.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCES);
	}

	public static final IDbIdField<AGMaintenance> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbDayTimeField<AGMaintenance> EXPECTED_START = BUILDER.addDayTimeField("expectedStart", o->o.m_expectedStart, (o,v)->o.m_expectedStart=v).setTitle(CoreI18n.EXPECTED_START);
	public static final IDbDayTimeField<AGMaintenance> EXPECTED_END = BUILDER.addDayTimeField("expectedEnd", o->o.m_expectedEnd, (o,v)->o.m_expectedEnd=v).setTitle(CoreI18n.EXPECTED_END);
	public static final IDbForeignField<AGMaintenance, AGMaintenanceStatus> STATUS = BUILDER.addForeignField("status", o->o.m_status, (o,v)->o.m_status=v, AGMaintenanceStatus.ID).setTitle(CoreI18n.STATUS);
	public static final IDbStringField<AGMaintenance> COMMENT = BUILDER.addStringField("comment", o->o.m_comment, (o,v)->o.m_comment=v).setTitle(CoreI18n.COMMENT).setNullable().setDefault(null);
	public static final AGMaintenanceTable TABLE = new AGMaintenanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGMaintenance> createSelect() {

		return TABLE.createSelect();
	}

	public static AGMaintenance get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final DayTime getExpectedStart() {

		return getValue(EXPECTED_START);
	}

	public final AGMaintenance setExpectedStart(DayTime value) {

		return setValue(EXPECTED_START, value);
	}

	public final DayTime getExpectedEnd() {

		return getValue(EXPECTED_END);
	}

	public final AGMaintenance setExpectedEnd(DayTime value) {

		return setValue(EXPECTED_END, value);
	}

	public final Integer getStatusID() {

		return getValueId(STATUS);
	}

	public final AGMaintenanceStatus getStatus() {

		return getValue(STATUS);
	}

	public final AGMaintenance setStatus(AGMaintenanceStatus value) {

		return setValue(STATUS, value);
	}

	public final String getComment() {

		return getValue(COMMENT);
	}

	public final AGMaintenance setComment(String value) {

		return setValue(COMMENT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGMaintenanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private DayTime m_expectedStart;
	private DayTime m_expectedEnd;
	private AGMaintenanceStatus m_status;
	private String m_comment;
}

