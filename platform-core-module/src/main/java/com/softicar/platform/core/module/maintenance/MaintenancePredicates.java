package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface MaintenancePredicates {

	IEmfPredicate<AGMaintenance> PENDING = new EmfPredicate<>(//
		CoreI18n.PENDING,
		AGMaintenance::isPending);

	IEmfPredicate<AGMaintenance> IN_PROGRESS = new EmfPredicate<>(//
		CoreI18n.IN_PROGRESS,
		AGMaintenance::isInProgress);
	IEmfPredicate<AGMaintenance> NEXT_IN_LINE = new EmfPredicate<>(//
		CoreI18n.NEXT_IN_LINE,
		AGMaintenance::isNextInLine);

	IEmfPredicate<AGMaintenance> MAINTENANCE_ACTIVE = new EmfPredicate<>(//
		CoreI18n.MAINTENANCE_ACTIVE,
		it -> AGMaintenance.isMaintenanceActive());

	IEmfPredicate<AGMaintenance> NO_MAINTENANCE_ACTIVE = MAINTENANCE_ACTIVE.not();
}
