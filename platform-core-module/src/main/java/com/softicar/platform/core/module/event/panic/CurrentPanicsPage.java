package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("409b569f-9705-4809-a181-da29dada248d")
public class CurrentPanicsPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.EVENT_LEVEL_PANIC.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CURRENT_PANIC_ENTRIES;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new CurrentPanicsDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.LOGGING);
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return CoreRoles.SUPER_USER;
	}
}
