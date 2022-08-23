package com.softicar.platform.core.module.module.overview;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;

@SourceCodeReferencePointUuid("eabae24f-5748-4c0b-9466-dbb609bf0dfe")
public class ModuleOverviewPage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.MODULES.getResource();
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.MODULE_CLASSES;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new ModuleOverviewPageDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.MODULES);
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ADMINISTRATION;
	}
}
