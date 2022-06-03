package com.softicar.platform.core.module.component.external;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

/**
 * A page that provides an overview of utilized external components (e.g.
 * runtime Java libraries), and their respective type, version and license.
 *
 * @author Alexander Schmidt
 */
@EmfSourceCodeReferencePointUuid("7061b293-2a3c-4df8-a386-66432511afb1")
public class ExternalComponentsPage implements IEmfPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDisplayString getTitle(SystemModuleInstance moduleInstance) {

		return CoreI18n.EXTERNAL_COMPONENTS;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		return new ExternalComponentsPageNode();
	}

	@Override
	public IEmfPermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.SUPER_USER;
	}
}
