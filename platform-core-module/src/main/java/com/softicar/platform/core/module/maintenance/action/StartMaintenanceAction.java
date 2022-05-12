package com.softicar.platform.core.module.maintenance.action;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.MaintenancePredicates;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public class StartMaintenanceAction implements IEmfManagementAction<AGMaintenanceWindow> {

	@Override
	public IEmfPredicate<AGMaintenanceWindow> getPrecondition() {

		return MaintenancePredicates.PENDING;
	}

	@Override
	public IEmfRole<AGMaintenanceWindow> getAuthorizedRole() {

		return CoreRoles.SYSTEM_ADMINISTRATOR.toOtherEntityRole();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MEDIA_PLAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.START_MAINTENANCE;
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(AGMaintenanceWindow maintenanceWindow) {

		if (maintenanceWindow.isNextInLine()) {
			return Optional.empty();
		} else {
			return Optional.of(() -> CoreI18n.ARE_YOU_SURE_QUESTION);
		}
	}

	@Override
	public void handleClick(AGMaintenanceWindow maintenance) {

		// TODO Add more logic in next PR
		// TODO Session-Invalidation of non-maintenance-admins
		// TODO Display Maintenance Info to Users
		try (DbTransaction transaction = new DbTransaction()) {
			if (AGMaintenanceWindow.isMaintenanceInProgress()) {
				throw new SofticarUserException(CoreI18n.A_MAINTENANCE_IS_ALREADY_IN_PROGRESS);
			}
			maintenance//
				.setState(AGMaintenanceStateEnum.IN_PROGRESS.getRecord())
				.save();
			transaction.commit();
		}
	}
}
