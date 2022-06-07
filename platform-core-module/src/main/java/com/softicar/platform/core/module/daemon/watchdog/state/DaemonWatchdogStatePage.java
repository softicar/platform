package com.softicar.platform.core.module.daemon.watchdog.state;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("5d5f4a7d-6569-45a9-a6d5-da6c57816dea")
public class DaemonWatchdogStatePage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(SystemModuleInstance moduleInstance) {

		return CoreI18n.DAEMON_WATCHDOG_STATE;
	}

	@Override
	public IEmfPermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.SUPER_USER;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new DaemonWatchdogStatePageDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}
}
