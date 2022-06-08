package com.softicar.platform.core.module.access.permission.assignment.module.instance.matrix.user;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("5e258795-6c07-4a4c-9694-b41124271a2d")
public class ModulePermissionOwnershipUserMatrixPage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IResource getIcon() {

		return EmfImages.USER_PERMISSION_ASSIGNMENT.getResource();
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.MODULE_INSTANCE_PERMISSION_ASSIGNMENT_MATRIX;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new ModulePermissionOwnershipUserMatrixPageDiv();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PERMISSIONS);
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		// TODO re-enable this page as soon as it's fixed
//		return CorePermissions.SUPER_USER;
		return EmfPermissions.never();
	}
}
