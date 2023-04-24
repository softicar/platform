package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.badge.EmfPageBadge;
import com.softicar.platform.emf.page.badge.EmfPageInfoBadge;
import com.softicar.platform.emf.page.badge.EmfPageWarningBadge;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@SourceCodeReferencePointUuid("3e68157b-990c-4148-a4e8-8c804104adb6")
public class MaintenanceWindowPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGMaintenanceWindow.TABLE;
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return EmfPermissions.any(CorePermissions.OPERATION, CorePermissions.ADMINISTRATION);
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.MAINTENANCE);
	}

	@Override
	public Collection<EmfPageBadge> getBadges(AGCoreModuleInstance moduleInstance) {

		Supplier<Integer> inProgressCountSupplier = () -> AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.isEqual(AGMaintenanceStateEnum.IN_PROGRESS.getRecord()))
			.count();
		Supplier<Integer> pendingCountSupplier = () -> AGMaintenanceWindow.TABLE//
			.createSelect()
			.where(AGMaintenanceWindow.STATE.isEqual(AGMaintenanceStateEnum.PENDING.getRecord()))
			.count();
		return List
			.of(//
				new EmfPageWarningBadge(inProgressCountSupplier)//
					.setTitle(CoreI18n.MAINTENANCE_WINDOWS_IN_PROGRESS)
					.setRefreshClasses(AGMaintenanceWindow.class),
				new EmfPageInfoBadge(pendingCountSupplier)//
					.setTitle(CoreI18n.PENDING_MAINTENANCE_WINDOWS)
					.setRefreshClasses(AGMaintenanceWindow.class));
	}
}
