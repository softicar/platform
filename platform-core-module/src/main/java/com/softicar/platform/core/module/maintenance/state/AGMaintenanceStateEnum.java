package com.softicar.platform.core.module.maintenance.state;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGMaintenanceStateEnum implements IDbEnumTableRowEnum<AGMaintenanceStateEnum, AGMaintenanceState> {

	PENDING(1, "Pending"),
	IN_PROGRESS(2, "In Progress"),
	FINISHED(3, "Finished"),
	CANCELED(4, "Canceled"),
	//
	;

	private Integer id;
	private String name;

	private AGMaintenanceStateEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGMaintenanceState, AGMaintenanceStateEnum> getTable() {

		return AGMaintenanceState.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGMaintenanceState> consumer) {

		consumer.consumeFieldValue(AGMaintenanceState.ID, id);
		consumer.consumeFieldValue(AGMaintenanceState.NAME, name);
	}
}

