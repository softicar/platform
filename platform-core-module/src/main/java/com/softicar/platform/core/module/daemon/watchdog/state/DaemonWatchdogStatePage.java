package com.softicar.platform.core.module.daemon.watchdog.state;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;

@SourceCodeReferencePointUuid("5d5f4a7d-6569-45a9-a6d5-da6c57816dea")
public class DaemonWatchdogStatePage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.DAEMON_WATCHDOG_STATE;
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.OPERATION;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new DaemonWatchdogStatePageDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}
}
