package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;

/**
 * This is the home page which is displayed to a user after logging in.
 *
 * @author Oliver Richers
 */
@SourceCodeReferencePointUuid("8411ad24-8ffe-45c8-aa7a-07812a1a5de0")
public class StartPage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.START_PAGE;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new StartPageDiv();
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return EmfPermissions.always();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return EmfPagePath.EMPTY_PATH;
	}
}
