package com.softicar.platform.core.module.component.external;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;

/**
 * A page that provides an overview of utilized external components (e.g.
 * runtime Java libraries), and their respective type, version and license.
 *
 * @author Alexander Schmidt
 */
@SourceCodeReferencePointUuid("7061b293-2a3c-4df8-a386-66432511afb1")
public class ExternalComponentsPage implements IEmfPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(AGCoreModuleInstance moduleInstance) {

		return CoreI18n.EXTERNAL_COMPONENTS;
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		return new ExternalComponentsPageNode();
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return EmfPermissions.always();
	}
}
