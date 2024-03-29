package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.DomTestMarker;
import java.util.function.Function;

public class DomModalAlertNodes<T> extends DomModalDialogNodes<T> implements IDomModalAlertNodes<T> {

	public DomModalAlertNodes(Function<ITestMarker, T> extractor) {

		super(extractor);
	}

	@Override
	public T getCloseButton() {

		return extractor.apply(DomTestMarker.MODAL_ALERT_CLOSE_BUTTON);
	}
}
