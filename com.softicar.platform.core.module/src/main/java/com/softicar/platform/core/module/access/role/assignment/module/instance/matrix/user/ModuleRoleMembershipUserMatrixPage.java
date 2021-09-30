package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("5e258795-6c07-4a4c-9694-b41124271a2d")
public class ModuleRoleMembershipUserMatrixPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return EmfImages.USER_ROLE_ASSIGNMENT.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.MODULE_INSTANCE_ROLE_ASSIGNMENT_MATRIX;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new ModuleRoleMembershipUserMatrixPageDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.ROLES);
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		// TODO re-enable this page as soon as it's fixed
//		return CoreRoles.SUPER_USER;
		return EmfRoles.nobody();
	}
}
