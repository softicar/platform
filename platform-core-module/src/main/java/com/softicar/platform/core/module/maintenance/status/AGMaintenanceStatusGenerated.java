package com.softicar.platform.core.module.maintenance.status;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGMaintenanceStatus for
 * database table <i>Core.MaintenanceStatus</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGMaintenanceStatusGenerated extends AbstractDbEnumTableRow<AGMaintenanceStatus, AGMaintenanceStatusEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGMaintenanceStatus, AGMaintenanceStatusGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "MaintenanceStatus", AGMaintenanceStatus::new, AGMaintenanceStatus.class);
	static {
		BUILDER.setTitle(CoreI18n.MAINTENANCE_STATUS);
		BUILDER.setPluralTitle(CoreI18n.MAINTENANCE_STATUSES);
	}

	public static final IDbIdField<AGMaintenanceStatus> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGMaintenanceStatus> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGMaintenanceStatus, AGMaintenanceStatusEnum> TABLE = new DbEnumTable<>(BUILDER, AGMaintenanceStatusEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGMaintenanceStatus setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGMaintenanceStatus, AGMaintenanceStatusEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

