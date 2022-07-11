package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.attribute.field.indirect.entity.IEmfIndirectEntity;

public class AGUuidBasedSourceCodeReferencePoint implements IEmfIndirectEntity<AGUuid> {

	private final ISourceCodeReferencePoint referencePoint;

	public AGUuidBasedSourceCodeReferencePoint(ISourceCodeReferencePoint referencePoint) {

		this.referencePoint = referencePoint;
	}

	@Override
	public IDisplayString toDisplay() {

		return referencePoint.toDisplay();
	}

	@Override
	public AGUuid getRepresentedEntity() {

		return AGUuid.getOrCreate(referencePoint);
	}
}
