package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.status.AGMaintenanceStatusEnum;
import com.softicar.platform.emf.object.IEmfObject;

public class AGMaintenance extends AGMaintenanceGenerated implements IEmfObject<AGMaintenance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.MAINTENANCE_FROM_ARG1_TO_ARG2.toDisplay(getExpectedStart(), getExpectedEnd());
	}

	public boolean isPending() {

		return getStatus().equals(AGMaintenanceStatusEnum.PENDING.getRecord());
	}

	public boolean isInProgress() {

		return getStatus().equals(AGMaintenanceStatusEnum.IN_PROGRESS.getRecord());
	}

	public boolean isNextInLine() {

		return AGMaintenance.TABLE//
			.createSelect()
			.where(AGMaintenance.STATUS.equal(AGMaintenanceStatusEnum.PENDING.getRecord()))
			.orderBy(AGMaintenance.EXPECTED_START)
			.getFirstAsOptional()
			.map(it -> it.equals(this))
			.orElse(isPending());
	}

	public static boolean isMaintenanceActive() {

		return AGMaintenance.TABLE//
			.createSelect()
			.where(AGMaintenance.STATUS.equal(AGMaintenanceStatusEnum.IN_PROGRESS.getRecord()))
			.exists();
	}
}
