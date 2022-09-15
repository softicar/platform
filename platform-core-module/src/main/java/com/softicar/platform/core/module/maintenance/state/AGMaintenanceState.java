package com.softicar.platform.core.module.maintenance.state;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class AGMaintenanceState extends AGMaintenanceStateGenerated implements IEntity {

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getEnum().toDisplay();
	}
}
