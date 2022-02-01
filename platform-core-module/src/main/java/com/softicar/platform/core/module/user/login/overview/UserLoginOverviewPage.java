package com.softicar.platform.core.module.user.login.overview;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("e70bc58f-9f60-4fa3-9450-6d7f383be31a")
public class UserLoginOverviewPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.USER_LOGIN_OVERVIEW;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new UserLoginOverviewDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.USERS).append(CoreI18n.LOGIN);
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return CoreRoles.SUPER_USER;
	}
}
