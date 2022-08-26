package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.DomTestMarker;
import java.util.function.Function;

public class DomModalDialogNodes<T> implements IDomModalDialogNodes<T> {

	protected final Function<ITestMarker, T> extractor;

	public DomModalDialogNodes(Function<ITestMarker, T> extractor) {

		this.extractor = extractor;
	}

	@Override
	public T getFrame() {

		return extractor.apply(DomTestMarker.MODAL_DIALOG_FRAME);
	}

	@Override
	public T getPopup() {

		return extractor.apply(DomTestMarker.MODAL_DIALOG_POPUP);
	}

	@Override
	public T getContent() {

		return extractor.apply(DomTestMarker.MODAL_DIALOG_CONTENT);
	}

	@Override
	public T getBackdrop() {

		return extractor.apply(DomTestMarker.POPUP_BACKDROP);
	}
}
