package com.softicar.platform.core.module.maintenance.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.maintenance.AGMaintenance;
import com.softicar.platform.core.module.maintenance.MaintenancePredicates;
import com.softicar.platform.core.module.maintenance.status.AGMaintenanceStatusEnum;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class CancelMaintenanceAction implements IEmfManagementAction<AGMaintenance> {

	@Override
	public IEmfPredicate<AGMaintenance> getPrecondition() {

		return MaintenancePredicates.PENDING;
	}

	@Override
	public IEmfRole<AGMaintenance> getAuthorizedRole() {

		return CoreRoles.MAINTENANCE_ADMINISTRATOR.toOtherEntityRole();
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
	public void handleClick(AGMaintenance maintenance) {

		try (DbTransaction transaction = new DbTransaction()) {
			maintenance//
				.setStatus(AGMaintenanceStatusEnum.CANCELED.getRecord())
				.save();
			transaction.commit();
		}
	}
}
