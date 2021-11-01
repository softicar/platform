package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid(TestPage.UUID)
public class TestPage implements IEmfPage<SystemModuleInstance> {

	public static final String UUID = "9b9913fc-6a0c-4a62-bc62-818dd773e6ae";
	public static final String CONTENT_STRING = "This is the test page.";

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IDomNode createContentNode(SystemModuleInstance moduleInstance) {

		DomDiv testDiv = new DomDiv();
		testDiv.appendText(CONTENT_STRING);
		return testDiv;
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Test Page");
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return EmfRoles.anybody();
	}
}
