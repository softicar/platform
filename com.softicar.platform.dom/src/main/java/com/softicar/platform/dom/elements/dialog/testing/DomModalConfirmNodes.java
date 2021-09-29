package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.dialog.DomModalConfirmMarker;
import java.util.function.Function;

public class DomModalConfirmNodes<T> extends DomModalDialogNodes<T> implements IDomModalConfirmNodes<T> {

	public DomModalConfirmNodes(Function<IStaticObject, T> extractor) {

		super(extractor);
	}

	@Override
	public T getOkayButton() {

		return extractor.apply(DomModalConfirmMarker.OKAY_BUTTON);
	}

	@Override
	public T getCancelButton() {

		return extractor.apply(DomModalConfirmMarker.CANCEL_BUTTON);
	}
}
