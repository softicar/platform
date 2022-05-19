package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Collection;
import java.util.Optional;

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

	public static Optional<AGMaintenanceWindow> getTodaysMaintenanceIfPresent() {

		return AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.isIn(AGMaintenanceStateEnum.PENDING.getRecord(), AGMaintenanceStateEnum.IN_PROGRESS.getRecord()))
			.where(AGMaintenanceWindow.EXPECTED_START.greaterEqual(DayTime.today()))
			.where(AGMaintenanceWindow.EXPECTED_END.isLess(DayTime.today().getTomorrow()))
			.getFirstAsOptional();
	}

	public static Collection<AGMaintenanceWindow> getAllPendingMaintenanceWindows() {

		return AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.equal(AGMaintenanceStateEnum.PENDING.getRecord()))
			.orderBy(AGMaintenanceWindow.EXPECTED_START)
			.list();
	}

	public static Optional<AGMaintenanceWindow> getMaintenanceInProgress() {

		return AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.equal(AGMaintenanceStateEnum.IN_PROGRESS.getRecord()))
			.getOneAsOptional();
	}

	public static boolean isMaintenanceInProgress() {

		return getMaintenanceInProgress().isPresent();
	}
}
