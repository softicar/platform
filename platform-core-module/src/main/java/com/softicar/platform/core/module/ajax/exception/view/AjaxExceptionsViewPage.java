package com.softicar.platform.core.module.ajax.exception.view;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("4e150ab4-bf63-41f9-9127-506a403e42c1")
public class AjaxExceptionsViewPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.EXCEPTION.getResource();
	}

	@Override
	public IDisplayString getTitle(SystemModuleInstance moduleInstance) {

		return CoreI18n.AJAX_EXCEPTIONS;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new AjaxExceptionsViewDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.EVENTS);
	}

	@Override
	public IEmfPermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.SUPER_USER;
	}
}
