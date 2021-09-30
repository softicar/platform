package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.attribute.field.indirect.entity.IEmfIndirectEntity;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

public class AGUuidBasedSourceCodeReferencePoint implements IEmfIndirectEntity<AGUuid> {

	private final IEmfSourceCodeReferencePoint referencePoint;

	public AGUuidBasedSourceCodeReferencePoint(IEmfSourceCodeReferencePoint referencePoint) {

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
