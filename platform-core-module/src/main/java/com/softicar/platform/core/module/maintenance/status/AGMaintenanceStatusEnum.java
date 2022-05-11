package com.softicar.platform.core.module.maintenance.status;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGMaintenanceStatusEnum implements IDbEnumTableRowEnum<AGMaintenanceStatusEnum, AGMaintenanceStatus> {

	PENDING(1, "Pending"),
	IN_PROGRESS(2, "In Progress"),
	FINISHED(3, "Finished"),
	CANCELED(4, "Canceled"),
	//
	;

	private Integer id;
	private String name;

	private AGMaintenanceStatusEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGMaintenanceStatus, AGMaintenanceStatusEnum> getTable() {

		return AGMaintenanceStatus.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGMaintenanceStatus> consumer) {

		consumer.consumeFieldValue(AGMaintenanceStatus.ID, id);
		consumer.consumeFieldValue(AGMaintenanceStatus.NAME, name);
	}
}

