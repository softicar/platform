package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.dialog.DomModalAlertMarker;
import java.util.function.Function;

public class DomModalAlertNodes<T> extends DomModalDialogNodes<T> implements IDomModalAlertNodes<T> {

	public DomModalAlertNodes(Function<IStaticObject, T> extractor) {

		super(extractor);
	}

	@Override
	public T getCloseButton() {

		return extractor.apply(DomModalAlertMarker.CLOSE_BUTTON);
	}
}
