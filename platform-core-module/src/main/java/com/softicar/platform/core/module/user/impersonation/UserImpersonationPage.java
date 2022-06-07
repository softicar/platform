package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("cff17c44-2186-40d1-8dca-5d82e2836918")
public class UserImpersonationPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.USER_IMPERSONATION.getResource();
	}

	@Override
	public IDisplayString getTitle(SystemModuleInstance moduleInstance) {

		return CoreI18n.IMPERSONATE_USER;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new UserImpersonationPageDiv();
	}

	@Override
	public IEmfPermission<SystemModuleInstance> getRequiredPermission() {

		return EmfPermissions.never();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.USERS);
	}
}
