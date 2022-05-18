package com.softicar.platform.demo.module.moment;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.core.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.core.module.DemoI18n;

/**
 * This is the automatically generated class AGDemoMoment for
 * database table <i>Demo.DemoMoment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoMomentGenerated extends AbstractDbObject<AGDemoMoment> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoMoment, AGDemoMomentGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoMoment", AGDemoMoment::new, AGDemoMoment.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_MOMENT);
		BUILDER.setPluralTitle(DemoI18n.DEMO_MOMENTS);
	}

	public static final IDbIdField<AGDemoMoment> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignRowField<AGDemoMoment, AGDemoModuleInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGDemoModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbDayField<AGDemoMoment> DAY = BUILDER.addDayField("day", o->o.m_day, (o,v)->o.m_day=v).setTitle(DemoI18n.DAY);
	public static final IDbTimeField<AGDemoMoment> TIME = BUILDER.addTimeField("time", o->o.m_time, (o,v)->o.m_time=v).setTitle(DemoI18n.TIME);
	public static final IDbDayTimeField<AGDemoMoment> POINT_IN_TIME = BUILDER.addDayTimeField("pointInTime", o->o.m_pointInTime, (o,v)->o.m_pointInTime=v).setTitle(DemoI18n.POINT_IN_TIME);
	public static final AGDemoMomentTable TABLE = new AGDemoMomentTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoMoment> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoMoment get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGDemoModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGDemoMoment setModuleInstance(AGDemoModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Day getDay() {

		return getValue(DAY);
	}

	public final AGDemoMoment setDay(Day value) {

		return setValue(DAY, value);
	}

	public final Time getTime() {

		return getValue(TIME);
	}

	public final AGDemoMoment setTime(Time value) {

		return setValue(TIME, value);
	}

	public final DayTime getPointInTime() {

		return getValue(POINT_IN_TIME);
	}

	public final AGDemoMoment setPointInTime(DayTime value) {

		return setValue(POINT_IN_TIME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoMomentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGDemoModuleInstance m_moduleInstance;
	private Day m_day;
	private Time m_time;
	private DayTime m_pointInTime;
}

