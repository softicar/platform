package com.softicar.platform.dom.elements.dialog.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.dialog.DomModalPromptMarker;
import java.util.function.Function;

public class DomModalPromptNodes<T> extends DomModalDialogNodes<T> implements IDomModalPromptNodes<T> {

	public DomModalPromptNodes(Function<IStaticObject, T> extractor) {

		super(extractor);
	}

	@Override
	public T getInputElement() {

		return extractor.apply(DomModalPromptMarker.INPUT_ELEMENT);
	}

	@Override
	public T getOkayButton() {

		return extractor.apply(DomModalPromptMarker.OKAY_BUTTON);
	}

	@Override
	public T getCancelButton() {

		return extractor.apply(DomModalPromptMarker.CANCEL_BUTTON);
	}
}
