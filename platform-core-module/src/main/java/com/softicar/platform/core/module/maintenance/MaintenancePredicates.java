package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface MaintenancePredicates {

	IEmfPredicate<AGMaintenanceWindow> PENDING = new EmfPredicate<>(//
		CoreI18n.PENDING,
		AGMaintenanceWindow::isPending);

	IEmfPredicate<AGMaintenanceWindow> IN_PROGRESS = new EmfPredicate<>(//
		CoreI18n.IN_PROGRESS,
		AGMaintenanceWindow::isInProgress);
}
