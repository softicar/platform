package com.softicar.platform.core.module.maintenance.state;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGMaintenanceState for
 * database table <i>Core.MaintenanceState</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceStateGenerated extends AbstractDbEnumTableRow<AGMaintenanceState, AGMaintenanceStateEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGMaintenanceState, AGMaintenanceStateGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "MaintenanceState", AGMaintenanceState::new, AGMaintenanceState.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE_STATE);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCE_STATES);
	}

	public static final IDbIdField<AGMaintenanceState> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGMaintenanceState> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGMaintenanceState, AGMaintenanceStateEnum> TABLE = new DbEnumTable<>(BUILDER, AGMaintenanceStateEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGMaintenanceState setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGMaintenanceState, AGMaintenanceStateEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

