package com.softicar.platform.core.module.maintenance.state;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGMaintenanceStateEnum implements IDbEnumTableRowEnum<AGMaintenanceStateEnum, AGMaintenanceState>, IDisplayable {

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
	public IDisplayString toDisplay() {

		switch (this) {
		case PENDING:
			return CoreI18n.PENDING;
		case IN_PROGRESS:
			return CoreI18n.IN_PROGRESS;
		case FINISHED:
			return CoreI18n.FINISHED;
		case CANCELED:
			return CoreI18n.CANCELED;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGMaintenanceState> consumer) {

		consumer.consumeFieldValue(AGMaintenanceState.ID, id);
		consumer.consumeFieldValue(AGMaintenanceState.NAME, name);
	}
}

