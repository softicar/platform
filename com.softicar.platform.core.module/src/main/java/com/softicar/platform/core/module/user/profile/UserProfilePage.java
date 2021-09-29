package com.softicar.platform.core.module.user.profile;

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

@EmfSourceCodeReferencePointUuid("5d317385-4f95-4f9b-a919-29a69bac2d0b")
public class UserProfilePage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.USER_PROFILE;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new UserProfilePageDiv();
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

		return CoreImages.USER_PROFILE.getResource();
	}
}
