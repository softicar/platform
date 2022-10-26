package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("b08c7fca-1fd5-4ac3-b64d-672223a0d304")
public class ProgramPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGProgram.TABLE;
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.OPERATION;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}

	@Override
	public IDomNode createContentNode(AGCoreModuleInstance moduleInstance) {

		var div = new DomDiv();
		div.appendChild(new ProgramMaintenanceMessageBar());
		div.appendChild(super.createContentNode(moduleInstance));
		return div;
	}
}
