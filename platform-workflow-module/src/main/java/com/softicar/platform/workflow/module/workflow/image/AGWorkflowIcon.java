package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.emf.object.IEmfObject;

public class AGWorkflowIcon extends AGWorkflowIconGenerated implements IEmfObject<AGWorkflowIcon> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public DomImage getImage() {

		return new DomImage(new StoredFileResource(getIcon()));
	}
}
