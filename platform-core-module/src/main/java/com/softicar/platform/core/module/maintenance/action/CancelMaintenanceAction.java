package com.softicar.platform.core.module.maintenance.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.MaintenancePredicates;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class CancelMaintenanceAction implements IEmfManagementAction<AGMaintenanceWindow> {

	@Override
	public IEmfPredicate<AGMaintenanceWindow> getPrecondition() {

		return MaintenancePredicates.PENDING.or(MaintenancePredicates.IN_PROGRESS);
	}

	@Override
	public IEmfPermission<AGMaintenanceWindow> getRequiredPermission() {

		return CoreModule.getAdministationPermission();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MEDIA_STOP.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CANCEL;
	}

	@Override
	public void handleClick(AGMaintenanceWindow maintenance) {

		try (DbTransaction transaction = new DbTransaction()) {
			maintenance//
				.setState(AGMaintenanceStateEnum.CANCELED.getRecord())
				.save();
			transaction.commit();
		}
	}
}
