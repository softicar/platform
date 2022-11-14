package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import java.util.Optional;

public class AGWorkflowIcon extends AGWorkflowIconGenerated implements IEmfObject<AGWorkflowIcon> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public DomImage getImage() {

		return new DomImage(new StoredFileResource(getIcon()));
	}

	public static Optional<AGWorkflowIcon> getByName(AGWorkflowModuleInstance moduleInstance, String name) {

		return AGWorkflowIcon//
			.createSelect()
			.where(AGWorkflowIcon.MODULE_INSTANCE.isEqual(moduleInstance))
			.where(AGWorkflowIcon.NAME.isEqual(name))
			.getOneAsOptional();
	}
}
