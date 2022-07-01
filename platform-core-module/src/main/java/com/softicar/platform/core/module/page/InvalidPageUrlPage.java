package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("2baf3a6a-902e-41a3-a15d-b95411288e93")
public class InvalidPageUrlPage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGCoreModuleInstance>> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new DomMessageDiv(DomMessageType.ERROR, CoreI18n.THE_REQUESTED_PAGE_DOES_NOT_EXIST);
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.INVALID_PAGE_URL;
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return EmfPermissions.always();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return EmfPagePath.EMPTY_PATH;
	}

	@Override
	public boolean isNavigatable() {

		return false;
	}
}
