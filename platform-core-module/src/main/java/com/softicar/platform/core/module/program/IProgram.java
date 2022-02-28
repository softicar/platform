package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

public interface IProgram extends IEmfSourceCodeReferencePoint {

	@Override
	default IDisplayString toDisplay() {

		return IDisplayString.create(getClass().getSimpleName());
	}

	void executeProgram();

	default String getDefaultCronExpression() {

		return "";
	}
}
