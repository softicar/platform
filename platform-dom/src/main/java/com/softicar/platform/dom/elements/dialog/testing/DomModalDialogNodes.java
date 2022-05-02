package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.dialog.DomModalDialogMarker;
import com.softicar.platform.dom.elements.popup.DomPopupMarker;
import java.util.function.Function;

public class DomModalDialogNodes<T> implements IDomModalDialogNodes<T> {

	protected final Function<IStaticObject, T> extractor;

	public DomModalDialogNodes(Function<IStaticObject, T> extractor) {

		this.extractor = extractor;
	}

	@Override
	public T getFrame() {

		return extractor.apply(DomModalDialogMarker.FRAME);
	}

	@Override
	public T getPopup() {

		return extractor.apply(DomModalDialogMarker.POPUP);
	}

	@Override
	public T getContent() {

		return extractor.apply(DomModalDialogMarker.CONTENT);
	}

	@Override
	public T getBackdrop() {

		return extractor.apply(DomPopupMarker.BACKDROP);
	}
}
