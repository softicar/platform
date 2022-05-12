package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.emf.object.IEmfObject;

public class AGMaintenanceWindow extends AGMaintenanceWindowGenerated implements IEmfObject<AGMaintenanceWindow> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.MAINTENANCE_WINDOW_FROM_ARG1_TO_ARG2.toDisplay(getExpectedStart(), getExpectedEnd());
	}

	public boolean isPending() {

		return getState().equals(AGMaintenanceStateEnum.PENDING.getRecord());
	}

	public boolean isInProgress() {

		return getState().equals(AGMaintenanceStateEnum.IN_PROGRESS.getRecord());
	}

	public boolean isNextInLine() {

		return AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.equal(AGMaintenanceStateEnum.PENDING.getRecord()))
			.orderBy(AGMaintenanceWindow.EXPECTED_START)
			.getFirstAsOptional()
			.map(it -> it.equals(this))
			.orElse(false);
	}

	public static boolean isMaintenanceInProgress() {

		return AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.equal(AGMaintenanceStateEnum.IN_PROGRESS.getRecord()))
			.exists();
	}
}
