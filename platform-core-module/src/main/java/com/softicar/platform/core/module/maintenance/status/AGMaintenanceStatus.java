package com.softicar.platform.core.module.maintenance.status;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;

public class AGMaintenanceStatus extends AGMaintenanceStatusGenerated implements IEntity {

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getDisplayString(getEnum());
	}

	private static IDisplayString getDisplayString(AGMaintenanceStatusEnum maintenanceStatusEnum) {

		switch (maintenanceStatusEnum) {
		case PENDING:
			return CoreI18n.PENDING;
		case IN_PROGRESS:
			return CoreI18n.IN_PROGRESS;
		case FINISHED:
			return CoreI18n.FINISHED;
		case CANCELED:
			return CoreI18n.CANCELED;
		}

		throw new SofticarUnknownEnumConstantException(maintenanceStatusEnum);
	}
}
