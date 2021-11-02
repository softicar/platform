package com.softicar.platform.emf.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.test.module.EmfTestModule;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

@EmfSourceCodeReferencePointUuid("bbf5a4f9-53ee-4a77-8e95-30736ce200ae")
public class EmfTestPage implements IEmfPage<EmfTestModuleInstance> {

	@Override
	public Class<? extends IEmfModule<EmfTestModuleInstance>> getModuleClass() {

		return EmfTestModule.class;
	}

	@Override
	public IDomNode createContentNode(EmfTestModuleInstance moduleInstance) {

		throw new UnsupportedOperationException();
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Test Page");
	}

	@Override
	public IEmfRole<EmfTestModuleInstance> getAuthorizedRole() {

		return EmfRoles.anybody();
	}
}
