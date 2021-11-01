package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;

public interface IEmfFormIndicator {

	IResource getIcon();

	IDisplayString getDisplayString();

	default IDisplayString getTitle() {

		return IDisplayString.EMPTY;
	}

	default Optional<INullaryVoidFunction> getClickAction() {

		return Optional.empty();
	}
}
