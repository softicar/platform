package com.softicar.platform.core.module.ajax.session.reset;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("cf70c843-658e-4b43-8f51-61ab6d19068c")
public class AjaxSessionPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(SystemModuleInstance moduleInstance) {

		return CoreI18n.SESSION;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new AjaxSessionPageDiv();
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return EmfRoles.anybody();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return EmfPagePath.EMPTY_PATH;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.SESSION.getResource();
	}
}
