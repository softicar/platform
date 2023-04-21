package com.softicar.platform.core.module.event;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.page.EmfPageBadge;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.Collection;
import java.util.List;

@SourceCodeReferencePointUuid("39723369-a6fd-4319-8149-2d71895386f3")
public class SystemEventPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.EVENTS);
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.OPERATION;
	}

	@Override
	public Collection<EmfPageBadge> getBadges(AGCoreModuleInstance moduleInstance) {

		return List.of(SystemEvents.getPageBadgeForNumberOfEventsToConfirm());
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGSystemEvent.TABLE;
	}
}
